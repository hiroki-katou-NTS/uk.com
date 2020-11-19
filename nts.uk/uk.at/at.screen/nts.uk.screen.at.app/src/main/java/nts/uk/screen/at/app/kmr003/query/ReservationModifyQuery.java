package nts.uk.screen.at.app.kmr003.query;

import lombok.AllArgsConstructor;
import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.task.parallel.ManagedParallelWithContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.app.find.reservation.bento.dto.*;
import nts.uk.ctx.at.record.app.find.reservation.bento.query.ListBentoResevationQuery;
import nts.uk.ctx.at.record.dom.require.RecordDomRequireService;
import nts.uk.ctx.at.record.dom.reservation.bento.*;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservationStateService;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenu;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenuRepository;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentomenuAdapter;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.SWkpHistExport;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.BentoMenuByClosingTime;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.BentoReservationClosingTime;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTime;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTimeFrame;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.BentoReservationSetting;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.BentoReservationSettingRepository;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.OperationDistinction;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.service.GetStampCardQuery;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.GetClosureStartForEmployee;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryItem;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryItemRepository;
import nts.uk.ctx.bs.employee.pub.employee.export.PersonEmpBasicInfoPub;
import nts.uk.ctx.bs.employee.pub.employee.export.dto.PersonEmpBasicInfoDto;
import nts.uk.ctx.bs.employee.pub.workplace.SWkpHistWrkLocationExport;
import nts.uk.ctx.bs.employee.pub.workplace.master.WorkplacePub;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Le Huu Dat
 */
@Stateless
public class ReservationModifyQuery {

    @Inject
    private BentoReservationSettingRepository bentoReservationSettingRepo;

    @Inject
    private GetStampCardQuery getStampCardQuery;

    @Inject
    private PersonEmpBasicInfoPub personEmpBasicInfoPub;

    @Inject
    private BentoMenuRepository bentoMenuRepo;

    @Inject
    private ListBentoResevationQuery listBentoResevationQuery;

    @Inject
    private WorkplacePub workplacePub;

    @Inject
    private RecordDomRequireService requireService;

    @Inject
    private ManagedParallelWithContext parallel;

    /**
     * 修正する予約を抽出する
     */
    public ReservationModifyDto getReservations(List<String> empIds,
                                                ReservationDate reservationDate,
                                                BentoReservationSearchConditionDto searchCondition) {
        ReservationModifyDto result = new ReservationModifyDto();
        List<ReservationModifyError> errors = new ArrayList<>();

        // 1:運用区分を取得
        String cid = AppContexts.user().companyId();
        BentoReservationSetting bentoReservationSetting = getBentoReservationSetting(cid);
        if (bentoReservationSetting == null) return null;

        // 2:勤務場所を取得
        Optional<WorkLocationCode> workLocationCodeOpt = getWorkLocationCode(bentoReservationSetting, reservationDate);

        // 5:
        // List<社員ID＞から打刻カードを全て取得する
        Map<String, StampNumber> stampCards = getStampCardQuery.getStampNumberBy(empIds);

        List<ReservationRegisterInfo> reservationRegisterInfos = stampCards.entrySet().stream()
                .map(x -> new ReservationRegisterInfo(x.getValue().v())).collect(Collectors.toList());

        // 3:
        // 社員ID(List)から個人社員基本情報を取得
        List<PersonEmpBasicInfoDto> empBasicInfos = personEmpBasicInfoPub.getPerEmpBasicInfo(empIds);

        // UI処理[16]
        if (searchCondition == BentoReservationSearchConditionDto.NEW_ORDER) {
            for (String empId : empIds) {
                if (stampCards.containsKey(empId)) {
                    continue;
                }

                Optional<PersonEmpBasicInfoDto> empBasicInfoOpt = empBasicInfos.stream().filter(x -> x.getEmployeeId().equals(empId)).findFirst();
                if (!empBasicInfoOpt.isPresent()) {
                    continue;
                }
                PersonEmpBasicInfoDto empBasicInfo = empBasicInfoOpt.get();
                //新規注文の場合誰か一人が打刻カードがない場合
                errors.add(new ReservationModifyError("Msg_1634", TextResource.localize("Msg_1634", empBasicInfo.getEmployeeCode(), empBasicInfo.getBusinessName())));
            }
        }

        // 4: 取得する
        // 弁当メニュー
        List<BentoMenu> bentoMenus = bentoMenuRepo.getBentoMenu(cid, reservationDate.getDate(),
                reservationDate.getClosingTimeFrame());
        if (!CollectionUtil.isEmpty(bentoMenus)) {
            BentoMenu bentoMenu = bentoMenus.get(0);
            BentoMenuByClosingTime menu;
            if (bentoReservationSetting.getOperationDistinction() == OperationDistinction.BY_COMPANY) {
                // 4.2: 会社ごと締め時刻別のメニュー
                menu = bentoMenu.getByClosingTime(Optional.empty());
            } else {
                // 4.1: 場所より締め時刻別のメニュー(勤務場所コード)
                menu = bentoMenu.getByClosingTime(workLocationCodeOpt);
            }

            // 4.3: 締め時刻を作る
            List<ClosingTimeDto> bentoClosingTimes = new ArrayList<>();
            BentoReservationClosingTime closingTime = menu.getClosingTime();
            ReservationClosingTime closingTime1 = closingTime.getClosingTime1();
            Integer start1 = closingTime1.getStart().isPresent() ? closingTime1.getStart().get().v() : null;
            bentoClosingTimes.add(new ClosingTimeDto(ReservationClosingTimeFrame.FRAME1.value, closingTime1.getReservationTimeName().v(), closingTime1.getFinish().v(), start1));

            Optional<ReservationClosingTime> closingTime2Opt = closingTime.getClosingTime2();
            if (closingTime2Opt.isPresent()) {
                ReservationClosingTime closingTime2 = closingTime2Opt.get();
                Integer start2 = closingTime2.getStart().isPresent() ? closingTime2.getStart().get().v() : null;
                bentoClosingTimes.add(new ClosingTimeDto(ReservationClosingTimeFrame.FRAME2.value, closingTime2.getReservationTimeName().v(), closingTime2.getFinish().v(), start2));
            }

            result.setBentoClosingTimes(bentoClosingTimes);

            // 5.1: ヘッダー情報を作る
            List<HeaderInfoDto> bentos;
            if (reservationDate.getClosingTimeFrame() == ReservationClosingTimeFrame.FRAME1){
                bentos = menu.getMenu1().stream().map(x -> new HeaderInfoDto(x.getUnit().v(), x.getName().v(), x.getFrameNo())).collect(Collectors.toList());
            } else {
                bentos = menu.getMenu2().stream().map(x -> new HeaderInfoDto(x.getUnit().v(), x.getName().v(), x.getFrameNo())).collect(Collectors.toList());
            }
            result.setBentos(bentos);
        }

        // UI処理[11]
        if (CollectionUtil.isEmpty(result.getBentos())) {
            throw new BusinessException("Msg_1604");
        }

        List<ReservationModifyEmployeeDto> reservationModifyEmps = new ArrayList<>();
        if (searchCondition != BentoReservationSearchConditionDto.NEW_ORDER) {
            // 6: 一覧弁当予約を取得する(検索条件, 期間, List<予約登録情報>, 勤務場所コード, 予約締め時刻枠)
            reservationModifyEmps = getReservationModifyEmps(reservationRegisterInfos, reservationDate,
                    workLocationCodeOpt, searchCondition, stampCards, empBasicInfos);
        } else {
            // 7: 注文してない社員IDを取得する(取得したList＜打刻カード＞,Input．予約対象日)
            reservationModifyEmps = getNewOrderReservationModifyEmps(reservationRegisterInfos, reservationDate, stampCards, empBasicInfos);
        }

        // 8: 弁当予約が修正できる状態を取得する(予約社員ID,予約対象日.年月日)
        List<EmployeeInfoMonthFinishDto> empFinishs = getEmpFinishs(empBasicInfos, reservationDate, searchCondition, reservationModifyEmps);

        result.setEmpFinishs(empFinishs);
        reservationModifyEmps = reservationModifyEmps.stream()
                .sorted(Comparator.comparing(ReservationModifyEmployeeDto::getReservationMemberCode, Comparator.naturalOrder())
                        .thenComparing(ReservationModifyEmployeeDto::getReservationCardNo, Comparator.naturalOrder()))
                .collect(Collectors.toList());
        result.setReservationModifyEmps(reservationModifyEmps);
        result.setErrors(errors);
        // 9: 予約の修正起動情報
        return result;
    }

    /**
     * 運用区分を取得
     */
    private BentoReservationSetting getBentoReservationSetting(String cid) {
        Optional<BentoReservationSetting> bentoReservationSettingOpt = bentoReservationSettingRepo.findByCId(cid);
        return bentoReservationSettingOpt.orElse(null);
    }

    /**
     * 勤務場所を取得
     */
    private Optional<WorkLocationCode> getWorkLocationCode(BentoReservationSetting bentoReservationSetting, ReservationDate reservationDate) {
        Optional<WorkLocationCode> workLocationCodeOpt = Optional.empty();
        if (bentoReservationSetting.getOperationDistinction() == OperationDistinction.BY_LOCATION) {
            String empId = AppContexts.user().employeeId();
            // 社員と基準日から所属職場履歴項目を取得する
            Optional<SWkpHistWrkLocationExport> sWkpHistWrkLocationOpt = workplacePub.findBySidWrkLocationCD(empId, reservationDate.getDate());
            if (sWkpHistWrkLocationOpt.isPresent()) {
                String wkpCode = sWkpHistWrkLocationOpt.get().getWorkLocationCd();
                if (wkpCode != null) {
                    workLocationCodeOpt = Optional.of(new WorkLocationCode(wkpCode));
                }
            }
        }
        return workLocationCodeOpt;
    }

    /**
     * 一覧弁当予約を取得する
     */
    private List<ReservationModifyEmployeeDto> getReservationModifyEmps(List<ReservationRegisterInfo> reservationRegisterInfos,
                                                                        ReservationDate reservationDate,
                                                                        Optional<WorkLocationCode> workLocationCodeOpt,
                                                                        BentoReservationSearchConditionDto searchCondition,
                                                                        Map<String, StampNumber> stampCards,
                                                                        List<PersonEmpBasicInfoDto> empBasicInfos) {
        // List<予約登録情報>.size > 0 AND Input．検索条件!=新規条件
        List<BentoReservation> bentoReservations = new ArrayList<>();
        if (!CollectionUtil.isEmpty(reservationRegisterInfos)) {
            DatePeriod datePeriod = new DatePeriod(reservationDate.getDate(), reservationDate.getDate());
            List<WorkLocationCode> workLocationCodes = new ArrayList<>();
            workLocationCodeOpt.ifPresent(workLocationCodes::add);
            // 一覧弁当予約を取得する
            bentoReservations = listBentoResevationQuery.getListBentoResevationQuery(searchCondition, datePeriod,
                    reservationRegisterInfos, workLocationCodes, reservationDate.getClosingTimeFrame());
        }
        List<ReservationModifyEmployeeDto> reservationModifyEmps = new ArrayList<>();
        for (BentoReservation bentoReservation : bentoReservations) {
            Optional<Map.Entry<String, StampNumber>> stampCardOpt = stampCards.entrySet().stream()
                    .filter(x -> x.getValue().v().equals(bentoReservation.getRegisterInfor().getReservationCardNo()))
                    .findFirst();
            if (!stampCardOpt.isPresent()) {
                // 新規注文の場合誰か一人が打刻カードがない場合
                // ※注文行を作らない
                continue;
            }
            String empId = stampCardOpt.get().getKey();

            Optional<PersonEmpBasicInfoDto> empBasicInfoOpt = empBasicInfos.stream()
                    .filter(x -> x.getEmployeeId().equals(empId))
                    .findFirst();
            if (!empBasicInfoOpt.isPresent()) continue;
            PersonEmpBasicInfoDto empBasicInfo = empBasicInfoOpt.get();

            // 6.1: 社員の予約情報を作る
            ReservationModifyEmployeeDto reservationModifyEmp = new ReservationModifyEmployeeDto();
            reservationModifyEmp.setReservationCardNo(bentoReservation.getRegisterInfor().getReservationCardNo());
            reservationModifyEmp.setReservationMemberId(empBasicInfo.getEmployeeId());
            reservationModifyEmp.setReservationMemberCode(empBasicInfo.getEmployeeCode());
            reservationModifyEmp.setReservationMemberName(empBasicInfo.getBusinessName());
            reservationModifyEmp.setReservationDate(bentoReservation.getReservationDate().getDate());

            Optional<BentoReservationDetail> detailOpt = bentoReservation.getBentoReservationDetails().stream().findFirst();
            detailOpt.ifPresent(bentoReservationDetail -> reservationModifyEmp.setReservationTime(bentoReservationDetail.getDateTime().toString("HH:mm")));

            reservationModifyEmp.setOrdered(bentoReservation.isOrdered());
            reservationModifyEmp.setClosingTimeFrame(bentoReservation.getReservationDate().getClosingTimeFrame().value);
            List<ReservationModifyDetailDto> reservationDetails = bentoReservation.getBentoReservationDetails()
                    .stream().map(x -> new ReservationModifyDetailDto(x.getBentoCount().v(), x.getFrameNo()))
                    .collect(Collectors.toList());
            reservationModifyEmp.setReservationDetails(reservationDetails);

            reservationModifyEmps.add(reservationModifyEmp);
        }
        return reservationModifyEmps;
    }

    private List<ReservationModifyEmployeeDto> getNewOrderReservationModifyEmps(List<ReservationRegisterInfo> reservationRegisterInfos,
                                                                                ReservationDate reservationDate,
                                                                                Map<String, StampNumber> stampCards,
                                                                                List<PersonEmpBasicInfoDto> empBasicInfos) {
        List<ReservationRegisterInfo> newOrderInfos = new ArrayList<>();
        if (!CollectionUtil.isEmpty(reservationRegisterInfos)) {
            DatePeriod datePeriod = new DatePeriod(reservationDate.getDate(), reservationDate.getDate());
            newOrderInfos = listBentoResevationQuery.getNewOrderDetail(datePeriod, reservationRegisterInfos,
                    reservationDate.getClosingTimeFrame());
        }

        List<ReservationModifyEmployeeDto> reservationModifyEmps = new ArrayList<>();
        for (ReservationRegisterInfo cardInfo : newOrderInfos) {
            Optional<Map.Entry<String, StampNumber>> stampCardOpt = stampCards.entrySet().stream()
                    .filter(x -> x.getValue().v().equals(cardInfo.getReservationCardNo()))
                    .findFirst();
            if (!stampCardOpt.isPresent()) {
                // 新規注文の場合誰か一人が打刻カードがない場合
                // ※注文行を作らない
                continue;
            }
            String empId = stampCardOpt.get().getKey();

            Optional<PersonEmpBasicInfoDto> empBasicInfoOpt = empBasicInfos.stream()
                    .filter(x -> x.getEmployeeId().equals(empId))
                    .findFirst();
            if (!empBasicInfoOpt.isPresent()) continue;
            PersonEmpBasicInfoDto empBasicInfo = empBasicInfoOpt.get();

            // 6.1: 社員の予約情報を作る
            ReservationModifyEmployeeDto reservationModifyEmp = new ReservationModifyEmployeeDto();
            reservationModifyEmp.setReservationCardNo(cardInfo.getReservationCardNo());
            reservationModifyEmp.setReservationMemberId(empBasicInfo.getEmployeeId());
            reservationModifyEmp.setReservationMemberCode(empBasicInfo.getEmployeeCode());
            reservationModifyEmp.setReservationMemberName(empBasicInfo.getBusinessName());
            reservationModifyEmp.setReservationDate(reservationDate.getDate());
            reservationModifyEmp.setClosingTimeFrame(reservationDate.getClosingTimeFrame().value);
            reservationModifyEmp.setReservationDetails(new ArrayList<>());

            reservationModifyEmps.add(reservationModifyEmp);
        }
        return reservationModifyEmps;
    }

    /**
     * 弁当予約が修正できる状態を取得する
     */
    private List<EmployeeInfoMonthFinishDto> getEmpFinishs(List<PersonEmpBasicInfoDto> empBasicInfos,
                                                           ReservationDate reservationDate,
                                                           BentoReservationSearchConditionDto searchCondition,
                                                           List<ReservationModifyEmployeeDto> reservationModifyEmps) {
        List<EmployeeInfoMonthFinishDto> empFinishs = new ArrayList<>();
        RequireImpl require = new RequireImpl(requireService);
        this.parallel.forEach(reservationModifyEmps, reservationInfo -> {
            // 弁当予約が強制修正できる状態を取得する
            boolean canModify = BentoReservationStateService.check(require, reservationInfo.getReservationMemberId(),
                    reservationDate.getDate());
            if (!canModify) {
                Optional<PersonEmpBasicInfoDto> empBasicInfoOpt = empBasicInfos.stream()
                        .filter(x -> x.getEmployeeId().equals(reservationInfo.getReservationMemberId())).findFirst();
                if (empBasicInfoOpt.isPresent()) {
                    PersonEmpBasicInfoDto empBasicInfo = empBasicInfoOpt.get();

                    // 8.2: 月締め処理が済んでいる社員情報を作る
                    if (searchCondition == BentoReservationSearchConditionDto.NEW_ORDER) {
                        empFinishs.add(new EmployeeInfoMonthFinishDto(empBasicInfo.getEmployeeCode(),
                                empBasicInfo.getBusinessName()));
                    }
                    // 8.1 生活値を更新
                    reservationInfo.setActivity(false);
                }
            }
        });
        return empFinishs;
    }

    @AllArgsConstructor
    private class RequireImpl implements BentoReservationStateService.Require {
        private RecordDomRequireService requireService;

        @Override
        public Optional<GeneralDate> getClosureStart(String employeeId) {
            val require = requireService.createRequire();
            val cacheCarrier = new CacheCarrier();
            // 社員に対応する締め開始日を取得する
            return GetClosureStartForEmployee.algorithm(require, cacheCarrier, employeeId);
        }
    }
}
