package nts.uk.screen.at.app.kdl036;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.request.app.find.application.common.service.other.output.ActualContentDisplayDto;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.shared.app.find.remainingnumber.subhdmana.dto.LeaveComDayOffManaDto;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.DigestionAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakDayOffMngRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemainRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveComDayOffManagement;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveManaDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveManagementData;
import nts.uk.ctx.at.shared.dom.vacation.setting.ApplyPermission;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveComSetRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComSetting;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosurePeriod;
import nts.uk.screen.at.app.dailyperformance.correction.closure.FindClosureDateService;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

@Stateless
public class HolidayWorkSubHolidayAssociationFinder {
    @Inject
    private FindClosureDateService closureService;

    @Inject
    private InterimRemainRepository interimMngRepo;

    @Inject
    private InterimBreakDayOffMngRepository interimBreakDayOffMngRepo;

    @Inject
    private LeaveManaDataRepository holidayWorkMngRepo;

    @Inject
    private OtherCommonAlgorithm otherCommonAlgorithm;

    @Inject
    private CompensLeaveComSetRepository comSubstVacationRepo;

    /**
     * 休出代休関連付けダイアログ起動
     * @param inputData
     * @return
     */
    public Kdl036OutputData init(Kdl036InputData inputData) {
        Optional<ClosurePeriod> closurePeriod = closureService.getClosurePeriod(inputData.getEmployeeId(), GeneralDate.today());
        if (!closurePeriod.isPresent())
            throw new BusinessException(new RawErrorMessage("Closure Period Not Found!"));

        List<HolidayWorkData> outputData = getSubsituteWorkData(
                inputData.getEmployeeId(),
                closurePeriod.get(),
                inputData.getManagementData().stream().map(LeaveComDayOffManaDto::toDomain).collect(Collectors.toList())
        );

        DatePeriod period = inputData.getEndDate() != null ? new DatePeriod(inputData.getStartDate(), inputData.getEndDate()) : DatePeriod.oneDay(inputData.getStartDate());
        List<GeneralDate> dates = new ArrayList<>();
        if (period.start().compareTo(period.end()) == 0) {
            // 申請期間から休日の申請日を取得する
            dates.addAll(otherCommonAlgorithm.lstDateIsHoliday(
                    inputData.getEmployeeId(),
                    period,
                    inputData.getActualContentDisplayList().stream().map(ActualContentDisplayDto::toDomain).collect(Collectors.toList())
            ));
        }

        return new Kdl036OutputData(
                inputData.getEmployeeId(),
                inputData.getTargetSelectionAtr(),
                period.datesBetween().stream().filter(d -> !dates.contains(d)).collect(Collectors.toList()),
                outputData,
                inputData.getDaysUnit()
        );
    }

    /**
     * 紐付可能な休日出勤データを取得する
     */
    private List<HolidayWorkData> getSubsituteWorkData(String employeeId, ClosurePeriod closurePeriod, List<LeaveComDayOffManagement> managementData) {
        List<HolidayWorkData> result = new ArrayList<>();

        // 暫定休出データを取得する
        result.addAll(getProvisionalDrawingData(employeeId, closurePeriod, managementData));

        // 確定休出データを取得する
        result.addAll(getFixedDrawingData(employeeId, closurePeriod, managementData));

        // 紐付け中の休出データを取得する
        result.addAll(getDrawingDataDuringLinking(employeeId, closurePeriod, managementData));

        result.sort(Comparator.comparing(HolidayWorkData::getHolidayWorkDate));
        return result;
    }

    /**
     * 暫定休出データを取得する
     */
    private List<HolidayWorkData> getProvisionalDrawingData(String employeeId, ClosurePeriod closurePeriod, List<LeaveComDayOffManagement> managementData) {
        // ドメインモデル「暫定残数管理データ」を取得する
        List<InterimRemain> remainData = interimMngRepo.getRemainBySidPriod(
                employeeId,
                new DatePeriod(closurePeriod.getPeriod().start(), closurePeriod.getPeriod().start().addYears(1)),
                RemainType.BREAK);
        if (!managementData.isEmpty()) {
            List<GeneralDate> outBreakDays = managementData.stream().map(i -> i.getAssocialInfo().getOutbreakDay()).collect(Collectors.toList());
            remainData = remainData.stream().filter(i -> !outBreakDays.contains(i.getYmd())).collect(Collectors.toList());
        }

        if (remainData.isEmpty()) return Collections.emptyList();

        // ドメインモデル「暫定休出管理データ」を取得する
        List<String> mngIds = remainData.stream().map(InterimRemain::getRemainManaID).collect(Collectors.toList());
        List<InterimBreakMng> recData = interimBreakDayOffMngRepo.getBreakByIds(mngIds)
                .stream().filter(i -> i.getUnUsedDays().v() > 0 && i.getUnUsedTimes().v() > 0).collect(Collectors.toList());

        List<HolidayWorkData> result = new ArrayList<>();
        for (InterimBreakMng recMng : recData) {
            remainData.stream().filter(d -> d.getRemainManaID().equals(recMng.getBreakMngId())).findFirst().ifPresent((InterimRemain remainMng) -> {
                HolidayWorkData data = new HolidayWorkData();
                data.setHolidayWorkDate(remainMng.getYmd());
                data.setRemainingNumber(recMng.getUnUsedDays().v());
                data.setExpirationDate(recMng.getExpirationDate());
                data.setDataType(remainMng.getCreatorAtr() == CreateAtr.RECORD || remainMng.getCreatorAtr() == CreateAtr.FLEXCOMPEN ? DataType.ACTUAL.value : DataType.APPLICATION_OR_SCHEDULE.value);
                data.setExpiringThisMonth(recMng.getExpirationDate().beforeOrEquals(closurePeriod.getPeriod().end()));
                result.add(data);
            });
        }

        return result;
    }

    /**
     * 確定休出データを取得する
     */
    private List<HolidayWorkData> getFixedDrawingData(String employeeId, ClosurePeriod closurePeriod, List<LeaveComDayOffManagement> managementData) {
        // ドメインモデル「休出管理データ」を取得する
        List<LeaveManagementData> payoutData = holidayWorkMngRepo.getBySidAndStateAtr(AppContexts.user().companyId(), employeeId, DigestionAtr.UNUSED)
                .stream().filter(i -> i.getUnUsedDays().v() > 0 && i.getUnUsedTimes().v() > 0 && !i.getComDayOffDate().isUnknownDate())
                .collect(Collectors.toList());
        if (!managementData.isEmpty()) {
            List<GeneralDate> outBreakDays = managementData.stream().map(i -> i.getAssocialInfo().getOutbreakDay()).collect(Collectors.toList());
            payoutData = payoutData.stream().filter(i -> !i.getComDayOffDate().getDayoffDate().isPresent() || !outBreakDays.contains(i.getComDayOffDate().getDayoffDate().get())).collect(Collectors.toList());
        }
        if (payoutData.isEmpty()) return Collections.emptyList();

        List<HolidayWorkData> result = new ArrayList<>();
        for (LeaveManagementData payout : payoutData) {
            HolidayWorkData data = new HolidayWorkData();
            data.setHolidayWorkDate(payout.getComDayOffDate().getDayoffDate().orElse(null));
            data.setRemainingNumber(payout.getUnUsedDays().v());
            data.setExpirationDate(payout.getExpiredDate());
            data.setDataType(DataType.ACTUAL.value);
            data.setExpiringThisMonth(payout.getExpiredDate().beforeOrEquals(closurePeriod.getPeriod().end()));
            result.add(data);
        }
        return result;
    }

    /**
     * 紐付け中の休出データを取得する
     */
    private List<HolidayWorkData> getDrawingDataDuringLinking(String employeeId, ClosurePeriod closurePeriod, List<LeaveComDayOffManagement> managementData) {
        if (managementData.isEmpty()) return Collections.emptyList();
        List<HolidayWorkData> result = new ArrayList<>();
        List<GeneralDate> outBreakDays = managementData.stream().map(i -> i.getAssocialInfo().getOutbreakDay()).collect(Collectors.toList());

        // ドメインモデル「暫定残数管理データ」を取得する
        List<InterimRemain> remainData = interimMngRepo.getDataBySidDates(employeeId, outBreakDays);
        remainData = remainData.stream().filter(i -> i.getRemainType() == RemainType.BREAK).collect(Collectors.toList());

        if (!remainData.isEmpty()) {
            // ドメインモデル「暫定休出管理データ」を取得する
            List<String> mngIds = remainData.stream().map(InterimRemain::getRemainManaID).collect(Collectors.toList());
            List<InterimBreakMng> recData = interimBreakDayOffMngRepo.getBreakByIds(mngIds);

            for (InterimBreakMng recMng : recData) {
                remainData.stream().filter(d -> d.getRemainManaID().equals(recMng.getBreakMngId())).findFirst().ifPresent((InterimRemain remainMng) -> {
                    HolidayWorkData data = new HolidayWorkData();
                    data.setHolidayWorkDate(remainMng.getYmd());
                    managementData.stream().filter(i -> i.getAssocialInfo().getOutbreakDay().compareTo(remainMng.getYmd()) == 0).findFirst().ifPresent(i -> {
                        data.setRemainingNumber(i.getAssocialInfo().getDayNumberUsed().v());
                    });
                    data.setExpirationDate(recMng.getExpirationDate());
                    data.setDataType(remainMng.getCreatorAtr() == CreateAtr.RECORD || remainMng.getCreatorAtr() == CreateAtr.FLEXCOMPEN ? DataType.ACTUAL.value : DataType.APPLICATION_OR_SCHEDULE.value);
                    data.setExpiringThisMonth(recMng.getExpirationDate().beforeOrEquals(closurePeriod.getPeriod().end()));
                    result.add(data);
                });
            }
        }

        // ドメインモデル「休出管理データ」を取得する
        List<LeaveManagementData> payoutData = holidayWorkMngRepo.getBySidAndDatOff(employeeId, outBreakDays)
                .stream().filter(i -> i.getSubHDAtr() != DigestionAtr.EXPIRED && !i.getComDayOffDate().isUnknownDate() && i.getUnUsedTimes().v() <= 0)
                .collect(Collectors.toList());

        if (!payoutData.isEmpty()) {
            for (LeaveManagementData payout : payoutData) {
                HolidayWorkData data = new HolidayWorkData();
                data.setHolidayWorkDate(payout.getComDayOffDate().getDayoffDate().orElse(null));
                managementData.stream().filter(i -> payout.getComDayOffDate().getDayoffDate().isPresent() && i.getAssocialInfo().getOutbreakDay().compareTo(payout.getComDayOffDate().getDayoffDate().get()) == 0).findFirst().ifPresent(i -> {
                    data.setRemainingNumber(i.getAssocialInfo().getDayNumberUsed().v());
                });
                data.setExpirationDate(payout.getExpiredDate());
                data.setDataType(DataType.ACTUAL.value);
                data.setExpiringThisMonth(payout.getExpiredDate().beforeOrEquals(closurePeriod.getPeriod().end()));
                result.add(data);
            }
        }

        return result;
    }

    /**
     * 関連付け対象の決定
     * @param inputData
     * @return
     */
    public List<LeaveComDayOffManaDto> determineAssociationTarget(Kdl036OutputData inputData) {
        String companyId = AppContexts.user().companyId();

        // 会社別の代休管理設定を取得する
        CompensatoryLeaveComSetting comSubstVacation = comSubstVacationRepo.find(companyId);
        if (comSubstVacation == null)
            throw new BusinessException(new RawErrorMessage("代休管理設定 Not Found!"));

        // INPUT．「List<休日出勤データ>」と「代休日リスト」を並び変える
        inputData.getSubstituteHolidayList().sort(Comparator.naturalOrder());
        inputData.getHolidayWorkInfoList().sort(Comparator.comparing(HolidayWorkData::getExpirationDate));

        List<LeaveComDayOffManaDto> result = new ArrayList<>();
        for (GeneralDate holiday : inputData.getSubstituteHolidayList()) {
            double requiredNumber = inputData.getDaysUnit();
            while (requiredNumber > 0.0) {
                // 同一日かチェックする
                if (inputData.getHolidayWorkInfoList().stream()
                        .anyMatch(holidayWorkData -> holiday.compareTo(holidayWorkData.getHolidayWorkDate()) == 0)) {
                    throw new BusinessException("Msg_1901");
                }

                // 先取り許可するかチェックする
                if (inputData.getHolidayWorkInfoList().stream()
                        .anyMatch(holidayWorkData -> comSubstVacation.getCompensatoryAcquisitionUse().getPreemptionPermit() == ApplyPermission.NOT_ALLOW
                                && holiday.before(holidayWorkData.getHolidayWorkDate()))) {
                    throw new BusinessException("Msg_1902");
                }

                for (HolidayWorkData holidayWorkData : inputData.getHolidayWorkInfoList()) {
                    if (holidayWorkData.getRemainingNumber() > 0) {
                        // ループ中の「代休日」と「休日出勤データ」の期限を確認する
                        if (!holiday.beforeOrEquals(holidayWorkData.getExpirationDate()))
                            throw new BusinessException("Msg_970");

                        // 「休出代休紐付け管理」を作成する
                        LeaveComDayOffManaDto substituteMng = new LeaveComDayOffManaDto(
                                inputData.getEmployeeId(),
                                holidayWorkData.getHolidayWorkDate(),
                                holiday,
                                inputData.getDaysUnit() >= holidayWorkData.getRemainingNumber() ? holidayWorkData.getRemainingNumber() : inputData.getDaysUnit(),
                                inputData.getTargetSelectionAtr()
                        );
                        result.add(substituteMng);

                        // INPUT．「List<休日出勤データ>」を更新する
                        holidayWorkData.setRemainingNumber(holidayWorkData.getRemainingNumber() - inputData.getDaysUnit());

                        // 「必要日数」を更新する
                        requiredNumber = requiredNumber - substituteMng.getDayNumberUsed();
                        break;
                    }
                }
            }
        }
        return result;
    }
}
