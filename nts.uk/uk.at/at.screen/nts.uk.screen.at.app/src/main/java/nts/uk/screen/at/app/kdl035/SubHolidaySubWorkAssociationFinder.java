package nts.uk.screen.at.app.kdl035;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.request.app.find.application.common.service.other.output.ActualContentDisplayDto;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.shared.app.find.remainingnumber.paymana.PayoutSubofHDManagementDto;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecAbasMngRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.DigestionAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemainRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutManagementData;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutManagementDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutSubofHDManagement;
import nts.uk.ctx.at.shared.dom.vacation.setting.ApplyPermission;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacationRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosurePeriod;
import nts.uk.screen.at.app.dailyperformance.correction.closure.FindClosureDateService;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

@Stateless
public class SubHolidaySubWorkAssociationFinder {
    @Inject
    private FindClosureDateService closureService;

    @Inject
    private InterimRemainRepository interimMngRepo;

    @Inject
    private InterimRecAbasMngRepository interimRecAbasMngRepo;

    @Inject
    private PayoutManagementDataRepository payoutMngRepo;

    @Inject
    private OtherCommonAlgorithm otherCommonAlgorithm;

    @Inject
    private ComSubstVacationRepository comSubstVacationRepo;

    /**
     * 振休振出関連付けダイアログ起動
     * @param inputData
     * @return
     */
    public Kdl035OutputData init(Kdl035InputData inputData) {
        Optional<ClosurePeriod> closurePeriod = closureService.getClosurePeriod(inputData.getEmployeeId(), GeneralDate.today());
        if (!closurePeriod.isPresent())
            throw new BusinessException("Closure Period Not Found!");

        List<SubstituteWorkData> outputData = getSubsituteWorkData(
                inputData.getEmployeeId(),
                closurePeriod.get(),
                inputData.getManagementData().stream().map(PayoutSubofHDManagementDto::toDomain).collect(Collectors.toList())
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

        return new Kdl035OutputData(
                inputData.getEmployeeId(),
                inputData.getTargetSelectionAtr(),
                period.datesBetween().stream().filter(d -> !dates.contains(d)).collect(Collectors.toList()),
                outputData,
                inputData.getDaysUnit()
        );
    }

    /**
     * 紐付可能な振出データを取得する
     */
    private List<SubstituteWorkData> getSubsituteWorkData(String employeeId, ClosurePeriod closurePeriod, List<PayoutSubofHDManagement> managementData) {
        List<SubstituteWorkData> result = new ArrayList<>();

        // 暫定振出データを取得する
        result.addAll(getProvisionalDrawingData(employeeId, closurePeriod, managementData));

        // 確定振出データを取得する
        result.addAll(getFixedDrawingData(employeeId, closurePeriod, managementData));

        // 紐付け中の振出データを取得する
        result.addAll(getDrawingDataDuringLinking(employeeId, closurePeriod, managementData));

        result.sort(Comparator.comparing(SubstituteWorkData::getSubstituteWorkDate));
        return result;
    }

    /**
     * 暫定振出データを取得する
     */
    private List<SubstituteWorkData> getProvisionalDrawingData(String employeeId, ClosurePeriod closurePeriod, List<PayoutSubofHDManagement> managementData) {
        // ドメインモデル「暫定残数管理データ」を取得する
        List<InterimRemain> remainData = interimMngRepo.getRemainBySidPriod(
                employeeId,
                new DatePeriod(closurePeriod.getPeriod().start(), closurePeriod.getPeriod().start().addYears(1)),
                RemainType.PICKINGUP);
        if (!managementData.isEmpty()) {
            List<GeneralDate> outBreakDays = managementData.stream().map(i -> i.getAssocialInfo().getOutbreakDay()).collect(Collectors.toList());
            remainData = remainData.stream().filter(i -> !outBreakDays.contains(i.getYmd())).collect(Collectors.toList());
        }

        if (remainData.isEmpty()) return Collections.emptyList();

        // ドメインモデル「暫定振出管理データ」を取得する
        List<String> mngIds = remainData.stream().map(InterimRemain::getRemainManaID).collect(Collectors.toList());
        List<InterimRecMng> recData = interimRecAbasMngRepo.getRecByIds(mngIds).stream().filter(i -> i.getUnUsedDays().v() > 0).collect(Collectors.toList());

        List<SubstituteWorkData> result = new ArrayList<>();
        for (InterimRecMng recMng : recData) {
            remainData.stream().filter(d -> d.getRemainManaID().equals(recMng.getRecruitmentMngId())).findFirst().ifPresent((InterimRemain remainMng) -> {
                SubstituteWorkData data = new SubstituteWorkData();
                data.setSubstituteWorkDate(remainMng.getYmd());
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
     * 確定振出データを取得する
     */
    private List<SubstituteWorkData> getFixedDrawingData(String employeeId, ClosurePeriod closurePeriod, List<PayoutSubofHDManagement> managementData) {
        // ドメインモデル「振出管理データ」を取得する
        List<PayoutManagementData> payoutData = payoutMngRepo.getByStateAtr(employeeId, DigestionAtr.UNUSED)
                .stream().filter(i -> i.getUnUsedDays().v() > 0 && !i.getPayoutDate().isUnknownDate())
                .collect(Collectors.toList());
        if (!managementData.isEmpty()) {
            List<GeneralDate> outBreakDays = managementData.stream().map(i -> i.getAssocialInfo().getOutbreakDay()).collect(Collectors.toList());
            payoutData = payoutData.stream().filter(i -> !i.getPayoutDate().getDayoffDate().isPresent() || !outBreakDays.contains(i.getPayoutDate().getDayoffDate().get())).collect(Collectors.toList());
        }
        if (payoutData.isEmpty()) return Collections.emptyList();

        List<SubstituteWorkData> result = new ArrayList<>();
        for (PayoutManagementData payout : payoutData) {
            SubstituteWorkData data = new SubstituteWorkData();
            data.setSubstituteWorkDate(payout.getPayoutDate().getDayoffDate().orElse(null));
            data.setRemainingNumber(payout.getUnUsedDays().v());
            data.setExpirationDate(payout.getExpiredDate());
            data.setDataType(DataType.ACTUAL.value);
            data.setExpiringThisMonth(payout.getExpiredDate().beforeOrEquals(closurePeriod.getPeriod().end()));
            result.add(data);
        }
        return result;
    }

    /**
     * 紐付け中の振出データを取得する
     */
    private List<SubstituteWorkData> getDrawingDataDuringLinking(String employeeId, ClosurePeriod closurePeriod, List<PayoutSubofHDManagement> managementData) {
        if (managementData.isEmpty()) return Collections.emptyList();
        List<SubstituteWorkData> result = new ArrayList<>();
        List<GeneralDate> outBreakDays = managementData.stream().map(i -> i.getAssocialInfo().getOutbreakDay()).collect(Collectors.toList());

        // ドメインモデル「暫定残数管理データ」を取得する
        List<InterimRemain> remainData = interimMngRepo.getDataBySidDates(employeeId, outBreakDays);
        remainData = remainData.stream().filter(i -> i.getRemainType() == RemainType.PICKINGUP).collect(Collectors.toList());

        if (!remainData.isEmpty()) {
            // ドメインモデル「暫定振出管理データ」を取得する
            List<String> mngIds = remainData.stream().map(InterimRemain::getRemainManaID).collect(Collectors.toList());
            List<InterimRecMng> recData = interimRecAbasMngRepo.getRecByIds(mngIds);

            for (InterimRecMng recMng : recData) {
                remainData.stream().filter(d -> d.getRemainManaID().equals(recMng.getRecruitmentMngId())).findFirst().ifPresent((InterimRemain remainMng) -> {
                    SubstituteWorkData data = new SubstituteWorkData();
                    data.setSubstituteWorkDate(remainMng.getYmd());
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

        // ドメインモデル「振出管理データ」を取得する
        List<PayoutManagementData> payoutData = payoutMngRepo.getByListPayoutDate(AppContexts.user().companyId(), employeeId, outBreakDays)
                .stream().filter(i -> i.getStateAtr() != DigestionAtr.EXPIRED && !i.getPayoutDate().isUnknownDate())
                .collect(Collectors.toList());

        if (!payoutData.isEmpty()) {
            for (PayoutManagementData payout : payoutData) {
                SubstituteWorkData data = new SubstituteWorkData();
                data.setSubstituteWorkDate(payout.getPayoutDate().getDayoffDate().orElse(null));
                managementData.stream().filter(i -> payout.getPayoutDate().getDayoffDate().isPresent() && i.getAssocialInfo().getOutbreakDay().compareTo(payout.getPayoutDate().getDayoffDate().get()) == 0).findFirst().ifPresent(i -> {
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
    public List<PayoutSubofHDManagementDto> determineAssociationTarget(Kdl035OutputData inputData) {
        String companyId = AppContexts.user().companyId();

        // 会社別の振休管理設定を取得する
        Optional<ComSubstVacation> comSubstVacation = comSubstVacationRepo.findById(companyId);
        if (!comSubstVacation.isPresent())
            throw new BusinessException("振休管理設定 Not Found!");

        // INPUT．「List<振出データ>」と「振休日リスト」を並び変える
        inputData.getSubstituteHolidayList().sort(Comparator.naturalOrder());
        inputData.getSubstituteWorkInfoList().sort(Comparator.comparing(SubstituteWorkData::getExpirationDate));

        List<PayoutSubofHDManagementDto> result = new ArrayList<>();
        for (GeneralDate holiday : inputData.getSubstituteHolidayList()) {
            double requiredNumber = inputData.getDaysUnit();
            while (requiredNumber > 0.0) {
                for (SubstituteWorkData subWorkData : inputData.getSubstituteWorkInfoList()) {
                    if (subWorkData.getRemainingNumber() > 0) {
                        // 同一日かチェックする
                        if (holiday.compareTo(subWorkData.getSubstituteWorkDate()) == 0)
                            throw new BusinessException("Msg_1900");

                        // 先取り許可するかチェックする
                        if (comSubstVacation.get().getSetting().getAllowPrepaidLeave() == ApplyPermission.NOT_ALLOW && holiday.before(subWorkData.getSubstituteWorkDate()))
                            throw new BusinessException("Msg_1899");

                        // ループ中の「振休日」と「振出データ」の期限を確認する
                        if (!holiday.beforeOrEquals(subWorkData.getExpirationDate()))
                            throw new BusinessException("Msg_839");

                        // 「振出振休紐付け管理」を作成する
                        PayoutSubofHDManagementDto substituteMng = new PayoutSubofHDManagementDto(
                                inputData.getEmployeeId(),
                                subWorkData.getSubstituteWorkDate(),
                                holiday,
                                inputData.getDaysUnit() >= subWorkData.getRemainingNumber() ? subWorkData.getRemainingNumber() : inputData.getDaysUnit(),
                                inputData.getTargetSelectionAtr()
                        );
                        result.add(substituteMng);

                        // INPUT．「List<休振出データ>」を更新する
                        subWorkData.setRemainingNumber(subWorkData.getRemainingNumber() - inputData.getDaysUnit());

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
