package nts.uk.screen.at.app.kdl035;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.request.app.find.application.common.service.other.output.ActualContentDisplayDto;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.shared.app.find.remainingnumber.paymana.PayoutSubofHDManagementDto;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecAbasMngRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.DigestionAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutManagementDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutSubofHDManaRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutSubofHDManagement;
import nts.uk.ctx.at.shared.dom.vacation.setting.ApplyPermission;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacationRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class SubHolidaySubWorkAssociationFinder {
    @Inject
    private InterimRecAbasMngRepository interimRecAbasMngRepo;

    @Inject
    private PayoutManagementDataRepository payoutMngRepo;

    @Inject
    private OtherCommonAlgorithm otherCommonAlgorithm;

    @Inject
    private ComSubstVacationRepository comSubstVacationRepo;

    @Inject
    private ClosureRepository closureRepo;

    @Inject
    private ClosureEmploymentRepository closureEmploymentRepo;

    @Inject
    private ShareEmploymentAdapter shareEmploymentAdapter;
    
    @Inject
    private PayoutSubofHDManaRepository payoutSubofHDManaRepository;

    /**
     * ?????????????????????????????????????????????
     * @param inputData
     * @return
     */
    public Kdl035OutputData init(Kdl035InputData inputData) {
        // ????????????????????????????????????????????????
        DatePeriod closurePeriod = ClosureService.findClosurePeriod(
                ClosureService.createRequireM3(closureRepo, closureEmploymentRepo, shareEmploymentAdapter),
                new CacheCarrier(),
                inputData.getEmployeeId(),
                GeneralDate.today()
        );

        List<SubstituteWorkData> outputData = getSubsituteWorkData(
                inputData.getEmployeeId(),
                closurePeriod,
                inputData.getManagementData().stream().map(PayoutSubofHDManagementDto::toDomain).collect(Collectors.toList())
        );

        DatePeriod period = inputData.getEndDate() != null ? new DatePeriod(inputData.getStartDate(), inputData.getEndDate()) : DatePeriod.oneDay(inputData.getStartDate());
        List<GeneralDate> dates = new ArrayList<>();
        if (period.start().compareTo(period.end()) != 0) {
            // ???????????????????????????????????????????????????
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
     * ?????????????????????????????????????????????
     */
    private List<SubstituteWorkData> getSubsituteWorkData(String employeeId, DatePeriod closurePeriod, List<PayoutSubofHDManagement> managementData) {
        List<SubstituteWorkData> result = new ArrayList<>();

        // ????????????????????????????????????
        result.addAll(getProvisionalDrawingData(employeeId, closurePeriod, managementData));

        // ????????????????????????????????????
        List<SubstituteWorkData> lstDrawingData = getFixedDrawingData(employeeId, closurePeriod, managementData);
        
        // ????????????????????????????????????
        result.addAll(this.organizeFixedData(employeeId, lstDrawingData));

        // ?????????????????????????????????????????????
        result.addAll(getDrawingDataDuringLinking(employeeId, closurePeriod, managementData));
        
        // ?????????????????????????????????????????????????????????????????????
//        List<PayoutSubofHDManagement> payoutSubofHDManagements = payoutSubofHDManaRepository.getByListOccDate(employeeId, result.stream().map(x -> x.getSubstituteWorkDate()).collect(Collectors.toList()));
//        List<GeneralDate> payoutDates = payoutSubofHDManagements.stream().map(x -> x.getAssocialInfo().getOutbreakDay()).collect(Collectors.toList());
//        
//        result = result.stream().filter(x -> !payoutDates.contains(x.getSubstituteWorkDate())).collect(Collectors.toList());
        

        result.sort(Comparator.comparing(SubstituteWorkData::getSubstituteWorkDate));
        return result;
    }

    /**
     * ????????????????????????????????????
     */
    private List<SubstituteWorkData> getProvisionalDrawingData(String employeeId, DatePeriod closurePeriod, List<PayoutSubofHDManagement> managementData) {
        List<GeneralDate> outbreakDays = managementData.stream().map(i -> i.getAssocialInfo().getOutbreakDay()).collect(Collectors.toList());

        // ?????????????????????????????????????????????????????????????????????
        List<SubstituteWorkData> result = interimRecAbasMngRepo.getRecBySidDatePeriod(
                employeeId,
                new DatePeriod(
                        closurePeriod.start(),
                        closurePeriod.start().addYears(1)
                )).stream()
                .filter(i -> !outbreakDays.contains(i.getYmd()) && i.getUnUsedDays().v() > 0)
                .map(recMng -> new SubstituteWorkData(
                        recMng.getCreatorAtr() == CreateAtr.RECORD || recMng.getCreatorAtr() == CreateAtr.FLEXCOMPEN ? DataType.ACTUAL.value : DataType.APPLICATION_OR_SCHEDULE.value,
                        recMng.getExpirationDate(),
                        recMng.getExpirationDate().beforeOrEquals(closurePeriod.end()),
                        recMng.getYmd(),
                        recMng.getUnUsedDays().v()
                )).collect(Collectors.toList());

        return result;
    }

    /**
     * ????????????????????????????????????
     */
    private List<SubstituteWorkData> getFixedDrawingData(String employeeId, DatePeriod closurePeriod, List<PayoutSubofHDManagement> managementData) {
        List<GeneralDate> outBreakDays = managementData.stream().map(i -> i.getAssocialInfo().getOutbreakDay()).collect(Collectors.toList());

        // ???????????????????????????????????????????????????????????????
        List<SubstituteWorkData> result = payoutMngRepo.getByStateAtr(employeeId, DigestionAtr.UNUSED)
                .stream()
                .filter(i -> !i.getPayoutDate().isUnknownDate()
                        && (!i.getPayoutDate().getDayoffDate().isPresent() || !outBreakDays.contains(i.getPayoutDate().getDayoffDate().get()))
                        && i.getUnUsedDays().v() > 0)
                .map(payout -> new SubstituteWorkData(
                        DataType.ACTUAL.value,
                        payout.getExpiredDate(),
                        payout.getExpiredDate().beforeOrEquals(closurePeriod.end()),
                        payout.getPayoutDate().getDayoffDate().orElse(null),
                        payout.getUnUsedDays().v()
                )).collect(Collectors.toList());

        return result;
    }

    /**
     * ?????????????????????????????????????????????
     */
    private List<SubstituteWorkData> getDrawingDataDuringLinking(String employeeId, DatePeriod closurePeriod, List<PayoutSubofHDManagement> managementData) {
        List<SubstituteWorkData> result = new ArrayList<>();

        if (managementData.isEmpty()) return result;

        List<GeneralDate> outBreakDays = managementData.stream().map(i -> i.getAssocialInfo().getOutbreakDay()).collect(Collectors.toList());

        // ?????????????????????????????????????????????????????????????????????
        // ?????????????????????????????????????????????????????????????????????
//        List<SubstituteWorkData> recData = interimRecAbasMngRepo.getRecBySidDatePeriod(
//                employeeId,
//                new DatePeriod(
//                        outBreakDays.stream().min(GeneralDate::compareTo).get(),
//                        outBreakDays.stream().max(GeneralDate::compareTo).get()
//                )).stream()
//                .filter(i -> outBreakDays.contains(i.getYmd()))
//                .map(recMng -> new SubstituteWorkData(
//                        recMng.getCreatorAtr() == CreateAtr.RECORD || recMng.getCreatorAtr() == CreateAtr.FLEXCOMPEN ? DataType.ACTUAL.value : DataType.APPLICATION_OR_SCHEDULE.value,
//                        recMng.getExpirationDate(),
//                        recMng.getExpirationDate().beforeOrEquals(closurePeriod.end()),
//                        recMng.getYmd(),
//                        recMng.getUnUsedDays().v()
//                )).collect(Collectors.toList());
        List<SubstituteWorkData> recData = interimRecAbasMngRepo.getRecBySidDatePeriod(
                employeeId,
                new DatePeriod(
                    outBreakDays.stream().min(GeneralDate::compareTo).get(),
                    outBreakDays.stream().max(GeneralDate::compareTo).get()
                )).stream()
                .filter(i -> outBreakDays.contains(i.getYmd()))
                    .map(recMng -> {
                                PayoutSubofHDManagement a = managementData.stream().filter(c -> c.getAssocialInfo().getOutbreakDay().equals(recMng.getYmd()))
                                    .collect(Collectors.toList()).get(0);
                                return new SubstituteWorkData(
                                    recMng.getCreatorAtr() == CreateAtr.RECORD || recMng.getCreatorAtr() == CreateAtr.FLEXCOMPEN ? DataType.ACTUAL.value : DataType.APPLICATION_OR_SCHEDULE.value,
                                    recMng.getExpirationDate(),
                                    recMng.getExpirationDate().beforeOrEquals(closurePeriod.end()),
                                    recMng.getYmd(),
                                    a.getAssocialInfo().getDayNumberUsed().v()
                                );}
                ).collect(Collectors.toList());
        result.addAll(recData);

        // ???????????????????????????????????????????????????????????????
        List<SubstituteWorkData> payoutData = payoutMngRepo.getByListPayoutDate(AppContexts.user().companyId(), employeeId, outBreakDays)
                .stream()
                .filter(i -> i.getStateAtr() != DigestionAtr.EXPIRED && !i.getPayoutDate().isUnknownDate())
                .map(payout -> new SubstituteWorkData(
                        DataType.ACTUAL.value,
                        payout.getExpiredDate(),
                        payout.getExpiredDate().beforeOrEquals(closurePeriod.end()),
                        payout.getPayoutDate().getDayoffDate().orElse(null),
                        payout.getUnUsedDays().v()
                )).collect(Collectors.toList());
        result.addAll(payoutData);

        return result;
    }

    /**
     * ???????????????????????????
     * @param inputData
     * @return
     */
    public List<PayoutSubofHDManagementDto> determineAssociationTarget(Kdl035OutputData inputData) {
        double required = inputData.getDaysUnit() * inputData.getSubstituteHolidayList().size();
        inputData.getSubstituteWorkInfoList().sort(Comparator.comparingDouble(SubstituteWorkData::getRemainingNumber).reversed());
        double total = 0;
        for (int i = 0; i < inputData.getSubstituteWorkInfoList().size(); i++) {
            if (total - required > 0.5) throw new BusinessException("Msg_1761");
            total += inputData.getSubstituteWorkInfoList().get(i).getRemainingNumber();
        }

        String companyId = AppContexts.user().companyId();

        // ?????????????????????????????????????????????
        Optional<ComSubstVacation> comSubstVacation = comSubstVacationRepo.findById(companyId);
        if (!comSubstVacation.isPresent())
            throw new BusinessException(new RawErrorMessage("?????????????????? Not Found!"));

        // INPUT??????List<???????????????>????????????????????????????????????????????????
        inputData.getSubstituteHolidayList().sort(Comparator.naturalOrder());
        inputData.getSubstituteWorkInfoList().sort(Comparator.comparing(SubstituteWorkData::getExpirationDate));

        List<PayoutSubofHDManagementDto> result = new ArrayList<>();
        for (GeneralDate holiday : inputData.getSubstituteHolidayList()) {
            double requiredNumber = inputData.getDaysUnit();
            while (requiredNumber > 0.0 && inputData.getSubstituteWorkInfoList().stream().anyMatch(i -> i.getRemainingNumber() > 0)) {
                // ??????????????????????????????
                if (inputData.getSubstituteWorkInfoList().stream()
                        .anyMatch(subWorkData -> holiday.compareTo(subWorkData.getSubstituteWorkDate()) == 0)) {
                    throw new BusinessException("Msg_1900");
                }

                // ??????????????????????????????????????????
                if (inputData.getSubstituteWorkInfoList().stream()
                        .anyMatch(subWorkData -> comSubstVacation.get().getSetting().getAllowPrepaidLeave() == ApplyPermission.NOT_ALLOW
                                && holiday.before(subWorkData.getSubstituteWorkDate()))) {
                    throw new BusinessException("Msg_1899");
                }

                for (SubstituteWorkData subWorkData : inputData.getSubstituteWorkInfoList()) {
                    if (subWorkData.getRemainingNumber() > 0) {
                        // ??????????????????????????????????????????????????????????????????????????????
                        if (!holiday.beforeOrEquals(subWorkData.getExpirationDate()))
                            throw new BusinessException("Msg_839");

                        // ????????????????????????????????????????????????
                        PayoutSubofHDManagementDto substituteMng = new PayoutSubofHDManagementDto(
                                inputData.getEmployeeId(),
                                subWorkData.getSubstituteWorkDate(),
                                holiday,
                                inputData.getDaysUnit() >= subWorkData.getRemainingNumber() ? subWorkData.getRemainingNumber() : inputData.getDaysUnit(),
                                inputData.getTargetSelectionAtr()
                        );
                        result.add(substituteMng);

                        // INPUT??????List<??????????????????>??????????????????
                        subWorkData.setRemainingNumber(subWorkData.getRemainingNumber() - inputData.getDaysUnit());

                        // ?????????????????????????????????
                        requiredNumber = requiredNumber - substituteMng.getDayNumberUsed();
                        break;
                    }
                }
            }
        }
        return result;
    }
    
    /**
     * ????????????????????????????????????
     */
    public List<SubstituteWorkData> organizeFixedData(String employeeId, List<SubstituteWorkData> lstDrawingData) {
        if (lstDrawingData.isEmpty()) {
            return lstDrawingData;
        }
        // ????????????????????????????????????????????????????????????
        List<PayoutSubofHDManagement> listPayoutSubofHDManagements = payoutSubofHDManaRepository.getByListOccDate(
                employeeId, 
                lstDrawingData.stream().map(x -> x.getSubstituteWorkDate()).collect(Collectors.toList()));
        
        Map<GeneralDate, Double> remainingByDate = new HashMap<GeneralDate, Double>();
        if (listPayoutSubofHDManagements.isEmpty()) {
            return lstDrawingData;
        }
        
        // ???????????????????????????List???Loop??????
        listPayoutSubofHDManagements.forEach(x -> {
            if (remainingByDate.containsKey(x.getAssocialInfo().getOutbreakDay())) {
                remainingByDate.put(x.getAssocialInfo().getOutbreakDay(), 
                        remainingByDate.get(x.getAssocialInfo().getOutbreakDay()) + x.getAssocialInfo().getDayNumberUsed().v());
            } else {
                remainingByDate.put(x.getAssocialInfo().getOutbreakDay(), x.getAssocialInfo().getDayNumberUsed().v());
            }
        });
        
        // ????????????????????????????????????????????????
        return lstDrawingData.stream().filter(x -> {
            return !remainingByDate.containsKey(x.getSubstituteWorkDate()) || (remainingByDate.containsKey(x.getSubstituteWorkDate()) && remainingByDate.get(x.getSubstituteWorkDate()) != x.getRemainingNumber());
        }).map(x -> {
            if (!remainingByDate.containsKey(x.getSubstituteWorkDate())) {
                return x;
            } else {
                Double remainingNumber = x.getRemainingNumber() - remainingByDate.get(x.getSubstituteWorkDate());
                x.setRemainingNumber(remainingNumber);
                return x;
            }
        }).collect(Collectors.toList());
    }
}
