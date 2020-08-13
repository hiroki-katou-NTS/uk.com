package nts.uk.screen.at.app.kmr003.query;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.app.find.reservation.bento.dto.*;
import nts.uk.ctx.at.record.app.find.reservation.bento.query.ListBentoResevationQuery;
import nts.uk.ctx.at.record.dom.reservation.bento.*;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenu;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenuRepository;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.BentoMenuByClosingTime;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.BentoReservationClosingTime;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTime;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTimeFrame;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.BentoReservationSetting;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.BentoReservationSettingRepository;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.OperationDistinction;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryItem;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryItemRepository;
import nts.uk.ctx.bs.employee.pub.employee.export.PersonEmpBasicInfoPub;
import nts.uk.ctx.bs.employee.pub.employee.export.dto.PersonEmpBasicInfoDto;
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
public class ReservationModificationQuery {

    @Inject
    private BentoReservationSettingRepository bentoReservationSettingRepo;

    @Inject
    private StampCardRepository stampCardRepo;

    @Inject
    private PersonEmpBasicInfoPub personEmpBasicInfoPub;

    @Inject
    private AffWorkplaceHistoryItemRepository affWorkplaceHistoryItemRepo;

    @Inject
    private BentoMenuRepository bentoMenuRepo;

    @Inject
    private ListBentoResevationQuery listBentoResevationQuery;

    @Inject
    private BentoReserveCommonService bentoReserveCommonService;

    /**
     * 修正する予約を抽出する
     */
    public ModificationInfoForReservationDto getReservations(List<String> empIds,
                                                             ReservationDate reservationDate,
                                                             BentoReservationSearchConditionDto searchCondition,
                                                             ReservationClosingTimeFrame reservationClosingTimeFrame) {
        ModificationInfoForReservationDto result = new ModificationInfoForReservationDto();

        // 1:運用区分を取得
        String cid = AppContexts.user().companyId();
        Optional<BentoReservationSetting> bentoReservationSettingOpt = bentoReservationSettingRepo.findByCId(cid);
        if (!bentoReservationSettingOpt.isPresent()) return null;
        BentoReservationSetting bentoReservationSetting = bentoReservationSettingOpt.get();

        // 2:勤務場所を取得
        String empId = AppContexts.user().employeeId();
        List<String> userIds = new ArrayList<>();
        userIds.add(empId);
        // 社員と基準日から所属職場履歴項目を取得する
        List<AffWorkplaceHistoryItem> wpHistItems = affWorkplaceHistoryItemRepo
                .getAffWrkplaHistItemByListEmpIdAndDateV2(reservationDate.getDate(), userIds);
        Optional<WorkLocationCode> workLocationCodeOpt = Optional.empty();
        if (!wpHistItems.isEmpty()) {
            AffWorkplaceHistoryItem wpHistItem = wpHistItems.get(0);
            if (wpHistItem.getWorkLocationCode().isPresent()) {
                workLocationCodeOpt = Optional.of(new WorkLocationCode(wpHistItem.getWorkLocationCode().get()));
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
                reservationClosingTimeFrame);
        if (!bentoMenus.isEmpty()) {
            BentoMenu bentoMenu = bentoMenus.get(0);

            // 5.1: ヘッダー情報を作る
            List<HeaderInfoDto> bentoHeaderInfos = bentoMenu.getMenu().stream().map(x ->
                    new HeaderInfoDto(x.getUnit().v(), x.getName().v(), x.getFrameNo())).collect(Collectors.toList());
            result.setBentoHeaderInfos(bentoHeaderInfos);

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
            Optional<ReservationClosingTime> closingTime2Opt = closingTime.getClosingTime2();
            if (reservationClosingTimeFrame == ReservationClosingTimeFrame.FRAME2 && closingTime2Opt.isPresent()) {
                ReservationClosingTime closingTime2 = closingTime2Opt.get();
                Integer start = closingTime2.getStart().isPresent() ? closingTime2.getStart().get().v() : null;
                bentoClosingTimes.add(new ClosingTimeDto(closingTime2.getReservationTimeName().v(), closingTime2.getFinish().v(), start));
            } else {
                Integer start = closingTime1.getStart().isPresent() ? closingTime1.getStart().get().v() : null;
                bentoClosingTimes.add(new ClosingTimeDto(closingTime1.getReservationTimeName().v(), closingTime1.getFinish().v(), start));
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
                    reservationRegisterInfos, workLocationCodes, reservationClosingTimeFrame);
        }

        // 7: 弁当予約が強制修正できる状態を取得する
        List<EmployeeInfoMonthFinishDto> empInfoMonthFinishs = new ArrayList<>();
        List<ReservationInfoForEmployeeDto> reservationInfoForEmps = new ArrayList<>();
        for (BentoReservation bentoReservation : bentoReservations){
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
            ReservationInfoForEmployeeDto reservationInfoForEmp = new ReservationInfoForEmployeeDto();
            reservationInfoForEmp.setReservationCardNo(stampCard.getStampNumber().v());
            reservationInfoForEmp.setReservationMemberId(empBasicInfo.getEmployeeId());
            reservationInfoForEmp.setReservationMemberCode(empBasicInfo.getEmployeeCode());
            reservationInfoForEmp.setReservationMemberName(empBasicInfo.getBusinessName());
            reservationInfoForEmp.setReservationDate(bentoReservation.getReservationDate().getDate());
            // reservationInfoForEmp.setReservationTime(); //TODO
            reservationInfoForEmp.setOrdered(bentoReservation.isOrdered());
            reservationInfoForEmp.setClosingTimeFrame(bentoReservation.getReservationDate().getClosingTimeFrame().value);
            List<ReservationDetailDto> reservationDetails = bentoReservation.getBentoReservationDetails()
                    .stream().map(x -> new ReservationDetailDto(x.getBentoCount().v(), x.getFrameNo()))
                    .collect(Collectors.toList());
            reservationInfoForEmp.setReservationDetails(reservationDetails);

            reservationInfoForEmps.add(reservationInfoForEmp);
        }

        for (PersonEmpBasicInfoDto empBasicInfo : empBasicInfos){
            boolean canModify = bentoReserveCommonService.canModifyReservation(empBasicInfo.getEmployeeId(),
                    reservationDate.getDate());
            if (!canModify){
                // 6.2: 月締め処理が済んでいる社員情報を作る
                if (searchCondition == BentoReservationSearchConditionDto.NEW_ORDER){
                    empInfoMonthFinishs.add(new EmployeeInfoMonthFinishDto(empBasicInfo.getEmployeeCode(),
                            empBasicInfo.getBusinessName()));
                }
                // 6.3 生活値を更新 //TODO
            }
        }
        result.setEmployeeInfoMonthFinishs(empInfoMonthFinishs);
        result.setReservationInfoForEmployees(reservationInfoForEmps);

        // 8: 予約の修正起動情報
        return result;
    }
}
