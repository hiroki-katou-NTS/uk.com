package nts.uk.ctx.at.request.ac.record.monthly.vacation.childcarenurse.childcare;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.pub.monthly.vacation.childcarenurse.childcare.*;
import nts.uk.ctx.at.request.dom.adapter.monthly.vacation.childcarenurse.*;
import nts.uk.ctx.at.request.dom.adapter.monthly.vacation.childcarenurse.childcare.GetRemainingNumberChildCareNurseAdapter;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.InterimRemainMngMode;
//import nts.uk.ctx.at.request.dom.adapter.monthly.vacation.childcarenurse.childcare.
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class GetRemainingNumberChildCareNurseAdapterImpl implements GetRemainingNumberChildCareNurseAdapter {
    @Inject
    private GetRemainingNumberChildCareNurse publisher;

    @Override
    public ChildCareNursePeriodImport getChildCareNurseRemNumWithinPeriod(
            String employeeId,
            DatePeriod period,
            InterimRemainMngMode performReferenceAtr,
            GeneralDate criteriaDate,
            Optional<Boolean> isOverWrite,
            Optional<List<TmpChildCareNurseMngWorkImport>> tempChildCareDataforOverWriteList,
            Optional<ChildCareNursePeriodImport> prevChildCareLeave,
            Optional<CreateAtr> createAtr,
            Optional<GeneralDate> periodOverWrite) {
        List< TmpChildCareNurseMngWorkExport > tmp = tempChildCareDataforOverWriteList.isPresent()
                ? tempChildCareDataforOverWriteList.get().stream().map(this::tmpChildCareNurseMngWorkToExport).collect(Collectors.toList())
                : null;
        ChildCareNursePeriodExport result = publisher.getChildCareNurseRemNumWithinPeriod(
                employeeId,
                period,
                performReferenceAtr,
                criteriaDate,
                isOverWrite,
                Optional.ofNullable(tmp),
                prevChildCareLeave.map(this::importToExport),
                createAtr,
                periodOverWrite);
        return exportToImport(result);
    }

    private ChildCareNursePeriodImport exportToImport(ChildCareNursePeriodExport export) {
        return new ChildCareNursePeriodImport(
        		//export.getChildCareNurseErrors().stream().map(i -> ChildCareNurseErrorsImport.of(i.getUsedNumber(), i.getLimitDays(), i.getYmd())).collect(Collectors.toList()),
        		export.getChildCareNurseErrors().stream().map(i -> ChildCareNurseErrorsImport.of(i.getUsedNumber(), i.getLimitDays(), i.getYmd())).collect(Collectors.toList()),


        		export.getAsOfPeriodEnd(),
                ChildCareNurseStartdateDaysInfoImport.of(
                        ChildCareNurseStartdateInfoImport.of(
                                export.getStartdateDays().getThisYear().getUsedDays(),
                                ChildCareNurseRemainingNumberImport.of(
                                        export.getStartdateDays().getThisYear().getRemainingNumber().getUsedDays(),
                                        export.getStartdateDays().getThisYear().getRemainingNumber().getUsedTime()
                                ),
                                export.getStartdateDays().getThisYear().getLimitDays()
                        ),
                        export.getStartdateDays().getNextYear().map(i -> ChildCareNurseStartdateInfoImport.of(
                                i.getUsedDays(),
                                ChildCareNurseRemainingNumberImport.of(
                                        i.getRemainingNumber().getUsedDays(),
                                        i.getRemainingNumber().getUsedTime()
                                ),
                                i.getLimitDays()
                        ))
                ),
                export.isStartDateAtr(),
                ChildCareNurseAggrPeriodDaysInfoImport.of(
                        ChildCareNurseAggrPeriodInfoImport.of(
                                export.getAggrperiodinfo().getThisYear().getUsedCount(),
                                export.getAggrperiodinfo().getThisYear().getUsedDays(),
                                export.getAggrperiodinfo().getThisYear().getAggrPeriodUsedNumber()
                        ),
                        export.getAggrperiodinfo().getNextYear().map(i -> ChildCareNurseAggrPeriodInfoImport.of(
                                i.getUsedCount(),
                                i.getUsedDays(),
                                i.getAggrPeriodUsedNumber()
                        ))
                )
        );
    }

    private ChildCareNursePeriodExport importToExport(ChildCareNursePeriodImport importData) {
        return new ChildCareNursePeriodExport(
                importData.getChildCareNurseErrors().stream().map(i -> ChildCareNurseErrorsExport.of(i.getUsedNumber(), i.getLimitDays(), i.getYmd())).collect(Collectors.toList()),
                importData.getAsOfPeriodEnd(),
                ChildCareNurseStartdateDaysInfoExport.of(
                        ChildCareNurseStartdateInfoExport.of(
                                importData.getStartdateDays().getThisYear().getUsedDays(),
                                ChildCareNurseRemainingNumberExport.of(
                                        importData.getStartdateDays().getThisYear().getRemainingNumber().getUsedDays(),
                                        importData.getStartdateDays().getThisYear().getRemainingNumber().getUsedTime()
                                ),
                                importData.getStartdateDays().getThisYear().getLimitDays()
                        ),
                        importData.getStartdateDays().getNextYear().map(i -> ChildCareNurseStartdateInfoExport.of(
                                i.getUsedDays(),
                                ChildCareNurseRemainingNumberExport.of(
                                        i.getRemainingNumber().getUsedDays(),
                                        i.getRemainingNumber().getUsedTime()
                                ),
                                i.getLimitDays()
                        ))
                ),
                importData.isStartDateAtr(),
                ChildCareNurseAggrPeriodDaysInfoExport.of(
                        ChildCareNurseAggrPeriodInfoExport.of(
                                importData.getAggrperiodinfo().getThisYear().getUsedCount(),
                                importData.getAggrperiodinfo().getThisYear().getUsedDays(),
                                importData.getAggrperiodinfo().getThisYear().getAggrPeriodUsedNumber()
                        ),
                        importData.getAggrperiodinfo().getNextYear().map(i -> ChildCareNurseAggrPeriodInfoExport.of(
                                i.getUsedCount(),
                                i.getUsedDays(),
                                i.getAggrPeriodUsedNumber()
                        ))
                )
        );
    }

    private TmpChildCareNurseMngWorkExport tmpChildCareNurseMngWorkToExport(TmpChildCareNurseMngWorkImport importData) {
        return new TmpChildCareNurseMngWorkExport(
                importData.getEmployeeId(),
                importData.getPeriod(),
                importData.getIsOverWrite(),
                importData.getTempChildCareDataforOverWriteList().stream().map(i -> tmpChildCareNurseMngWorkToExport(i)).collect(Collectors.toList()),
                importData.getCreatorAtr(),
                importData.getPeriodOverWrite()
        );
    }
}
