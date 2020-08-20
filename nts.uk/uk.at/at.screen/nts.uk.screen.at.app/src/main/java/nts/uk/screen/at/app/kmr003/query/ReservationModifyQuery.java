package nts.uk.screen.at.app.kmr003.query;

import lombok.AllArgsConstructor;
import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
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
import nts.uk.ctx.at.shared.dom.workrule.closure.service.GetClosureStartForEmployee;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryItem;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryItemRepository;
import nts.uk.ctx.bs.employee.pub.employee.export.PersonEmpBasicInfoPub;
import nts.uk.ctx.bs.employee.pub.employee.export.dto.PersonEmpBasicInfoDto;
import nts.uk.ctx.bs.employee.pub.workplace.SWkpHistWrkLocationExport;
import nts.uk.ctx.bs.employee.pub.workplace.master.WorkplacePub;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Le Huu Dat
 */
@Stateless
public class ReservationModifyQuery {

    @Inject
    private BentoReservationSettingRepository bentoReservationSettingRepo;

    @Inject
    private StampCardRepository stampCardRepo;

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

    /**
     * 修正する予約を抽出する
     */
    public ReservationModifyDto getReservations(List<String> empIds,
                                                ReservationDate reservationDate,
                                                BentoReservationSearchConditionDto searchCondition) {
        ReservationModifyDto result = new ReservationModifyDto();

        // 1:運用区分を取得
        String cid = AppContexts.user().companyId();
        Optional<BentoReservationSetting> bentoReservationSettingOpt = bentoReservationSettingRepo.findByCId(cid);
        if (!bentoReservationSettingOpt.isPresent()) return null;
        BentoReservationSetting bentoReservationSetting = bentoReservationSettingOpt.get();

        // 2:勤務場所を取得
        String empId = AppContexts.user().employeeId();
        Optional<WorkLocationCode> workLocationCodeOpt = Optional.empty();
        // 社員と基準日から所属職場履歴項目を取得する
        Optional<SWkpHistWrkLocationExport> sWkpHistWrkLocationOpt = workplacePub.findBySidWrkLocationCD(empId, reservationDate.getDate());
        if (sWkpHistWrkLocationOpt.isPresent()) {
            String wkpCode = sWkpHistWrkLocationOpt.get().getWorkLocationCd();
            System.out.println(wkpCode);
            if (wkpCode != null){
                workLocationCodeOpt = Optional.of(new WorkLocationCode(wkpCode));
            }
        }

        // 5:
        // List<社員ID＞から打刻カードを全て取得する
        List<StampCard> stampCards = stampCardRepo.getLstStampCardByLstSid(empIds);
        List<ReservationRegisterInfo> reservationRegisterInfos = stampCards.stream()
                .map(x -> new ReservationRegisterInfo(x.getStampNumber().v())).collect(Collectors.toList());

        // 3:
        // 社員ID(List)から個人社員基本情報を取得
        List<PersonEmpBasicInfoDto> empBasicInfos = personEmpBasicInfoPub.getPerEmpBasicInfo(empIds);

        // 4: 取得する
        // 弁当メニュー
        List<BentoMenu> bentoMenus = bentoMenuRepo.getBentoMenu(cid, reservationDate.getDate(),
                reservationDate.getClosingTimeFrame());
        if (!bentoMenus.isEmpty()) {
            BentoMenu bentoMenu = bentoMenus.get(0);

            // 5.1: ヘッダー情報を作る
            List<HeaderInfoDto> bentos = bentoMenu.getMenu().stream().map(x ->
                    new HeaderInfoDto(x.getUnit().v(), x.getName().v(), x.getFrameNo())).collect(Collectors.toList());
            result.setBentos(bentos);

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
        }

        // 6:
        List<BentoReservation> bentoReservations = new ArrayList<>();
        if (!reservationRegisterInfos.isEmpty()) {
            DatePeriod datePeriod = new DatePeriod(reservationDate.getDate(), reservationDate.getDate());
            List<WorkLocationCode> workLocationCodes = new ArrayList<>();
            workLocationCodeOpt.ifPresent(workLocationCodes::add);
            // 一覧弁当予約を取得する
            bentoReservations = listBentoResevationQuery.getListBentoResevationQuery(searchCondition, datePeriod,
                    reservationRegisterInfos, workLocationCodes, reservationDate.getClosingTimeFrame());
        }

        // 7: 弁当予約が強制修正できる状態を取得する
        List<EmployeeInfoMonthFinishDto> empFinishs = new ArrayList<>();
        List<ReservationModifyEmployeeDto> reservationModifyEmps = new ArrayList<>();
        for (BentoReservation bentoReservation : bentoReservations) {
            Optional<StampCard> stampCardOpt = stampCards.stream()
                    .filter(x -> x.getStampNumber().v().equals(bentoReservation.getRegisterInfor().getReservationCardNo()))
                    .findFirst();
            if (!stampCardOpt.isPresent()) continue;
            StampCard stampCard = stampCardOpt.get();

            Optional<PersonEmpBasicInfoDto> empBasicInfoOpt = empBasicInfos.stream()
                    .filter(x -> x.getEmployeeId().equals(stampCard.getEmployeeId()))
                    .findFirst();
            if (!empBasicInfoOpt.isPresent()) continue;
            PersonEmpBasicInfoDto empBasicInfo = empBasicInfoOpt.get();

            // 6.1: 社員の予約情報を作る
            ReservationModifyEmployeeDto reservationModifyEmp = new ReservationModifyEmployeeDto();
            reservationModifyEmp.setReservationCardNo(stampCard.getStampNumber().v());
            reservationModifyEmp.setReservationMemberId(empBasicInfo.getEmployeeId());
            reservationModifyEmp.setReservationMemberCode(empBasicInfo.getEmployeeCode());
            reservationModifyEmp.setReservationMemberName(empBasicInfo.getBusinessName());
            reservationModifyEmp.setReservationDate(bentoReservation.getReservationDate().getDate());

            Optional<BentoReservationDetail> detailOpt = bentoReservation.getBentoReservationDetails().stream().findFirst();
            detailOpt.ifPresent(bentoReservationDetail -> reservationModifyEmp.setReservationTime(bentoReservationDetail.getDateTime()));

            reservationModifyEmp.setClosingTimeFrame(bentoReservation.getReservationDate().getClosingTimeFrame().value);
            List<ReservationModifyDetailDto> reservationDetails = bentoReservation.getBentoReservationDetails()
                    .stream().map(x -> new ReservationModifyDetailDto(x.getBentoCount().v(), x.getFrameNo()))
                    .collect(Collectors.toList());
            reservationModifyEmp.setReservationDetails(reservationDetails);

            reservationModifyEmps.add(reservationModifyEmp);
        }

        for (PersonEmpBasicInfoDto empBasicInfo : empBasicInfos) {
            RequireImpl require = new RequireImpl(requireService);
            boolean canModify = BentoReservationStateService.check(require, empBasicInfo.getEmployeeId(),
                    reservationDate.getDate());
            if (!canModify) {
                // 6.2: 月締め処理が済んでいる社員情報を作る
                if (searchCondition == BentoReservationSearchConditionDto.NEW_ORDER) {
                    empFinishs.add(new EmployeeInfoMonthFinishDto(empBasicInfo.getEmployeeCode(),
                            empBasicInfo.getBusinessName()));
                }
                // 6.3 生活値を更新
                Optional<ReservationModifyEmployeeDto> reservationInfoForEmpOp = reservationModifyEmps.stream()
                        .filter(x -> x.getReservationMemberId().equals(empBasicInfo.getEmployeeId())).findFirst();
                reservationInfoForEmpOp.ifPresent(x -> x.setActivity(false));
            }
        }
        result.setEmpFinishs(empFinishs);
        result.setReservationModifyEmps(reservationModifyEmps);

        // 8: 予約の修正起動情報
        return result;
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
