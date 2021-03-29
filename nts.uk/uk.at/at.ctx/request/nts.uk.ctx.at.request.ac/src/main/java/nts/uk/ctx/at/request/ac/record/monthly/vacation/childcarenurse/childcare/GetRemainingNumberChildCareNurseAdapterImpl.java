package nts.uk.ctx.at.request.ac.record.monthly.vacation.childcarenurse.childcare;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.AggrResultOfChildCareNurse;
import nts.uk.ctx.at.record.pub.monthly.vacation.childcarenurse.childcare.*;
import nts.uk.ctx.at.request.dom.adapter.monthly.vacation.childcarenurse.*;
import nts.uk.ctx.at.request.dom.adapter.monthly.vacation.childcarenurse.childcare.GetRemainingNumberChildCareNurseAdapter;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.InterimRemainMngMode;
//import nts.uk.ctx.at.request.dom.adapter.monthly.vacation.childcarenurse.childcare.
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.interimdata.TempChildCareNurseManagement;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.DayNumberOfUse;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.TimeOfUse;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.ChildCareNurseUsedNumber;
import javax.ejb.Stateless;
import javax.inject.Inject;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class GetRemainingNumberChildCareNurseAdapterImpl implements GetRemainingNumberChildCareNurseAdapter {
    @Inject
    private GetRemainingNumberChildCareNursePub publisher;

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
            Optional<DatePeriod> periodOverWrite) {

        List< TmpChildCareNurseMngWorkExport > tmp = tempChildCareDataforOverWriteList.isPresent()
                ? tempChildCareDataforOverWriteList.get().stream().map(this::tmpChildCareNurseMngWorkToExport).collect(Collectors.toList())
                : null;

        ChildCareNursePeriodExport result = publisher.getChildCareRemNumWithinPeriod(
        		AppContexts.user().companyId(),
                employeeId,
                period,
                performReferenceAtr,
                criteriaDate,
                isOverWrite,
                new ArrayList<>(),
                Optional.empty(),
                createAtr,
                periodOverWrite);

        return exportToImport(result);
    }

    private ChildCareNursePeriodImport exportToImport(ChildCareNursePeriodExport export) {
        return new ChildCareNursePeriodImport(
        		//export.getChildCareNurseErrors().stream().map(i -> ChildCareNurseErrorsImport.of(i.getUsedNumber(), i.getLimitDays(), i.getYmd())).collect(Collectors.toList()),
//        		export.getChildCareNurseErrors()
//        		.stream()
//        		.map(i ->{
//        			DayNumberOfUse days =new  DayNumberOfUse(i.getUsedNumber().getUsedDay());
//        			Optional<TimeOfUse> times = Optional.of(new TimeOfUse(i.getUsedNumber().getUsedTimes().map(c->c).orElse(0))) ;
//
//        			return ChildCareNurseErrorsImport.of(ChildCareNurseUsedNumber.of(days,times), i.getLimitDays(), i.getYmd());
//
//        		})
//        		.collect(Collectors.toList()),
null,

//        		export.getAsOfPeriodEnd(),
null,
//                ChildCareNurseStartdateDaysInfoImport.of(
//                        ChildCareNurseStartdateInfoImport.of(
//                                export.getStartdateDays().getThisYear().getUsedDays(),
//                                ChildCareNurseRemainingNumberImport.of(
//                                        export.getStartdateDays().getThisYear().getRemainingNumber().getUsedDays(),
//                                        export.getStartdateDays().getThisYear().getRemainingNumber().getUsedTime()
//                                ),
//                                export.getStartdateDays().getThisYear().getLimitDays()
//                        ),
//                        export.getStartdateDays().getNextYear().map(i -> ChildCareNurseStartdateInfoImport.of(
//                                i.getUsedDays(),
//                                ChildCareNurseRemainingNumberImport.of(
//                                        i.getRemainingNumber().getUsedDays(),
//                                        i.getRemainingNumber().getUsedTime()
//                                ),
//                                i.getLimitDays()
//                        ))
//                ),
null,
//                export.isStartDateAtr(),
true,
//                ChildCareNurseAggrPeriodDaysInfoImport.of(
//                        ChildCareNurseAggrPeriodInfoImport.of(
//                                export.getAggrperiodinfo().getThisYear().getUsedCount(),
//                                export.getAggrperiodinfo().getThisYear().getUsedDays(),
//                                export.getAggrperiodinfo().getThisYear().getAggrPeriodUsedNumber()
//                        ),
//                        export.getAggrperiodinfo().getNextYear().map(i -> ChildCareNurseAggrPeriodInfoImport.of(
//                                i.getUsedCount(),
//                                i.getUsedDays(),
//                                i.getAggrPeriodUsedNumber()
//                        ))
//                )
null
        );
    }

    private ChildCareNursePeriodExport importToExport(ChildCareNursePeriodImport importData) {
        return new ChildCareNursePeriodExport(
                importData.getChildCareNurseErrors()
                .stream()
                .map(i ->{
                	Double days = i.getUsedNumber().getUsedDay().v();
                	Integer times = i.getUsedNumber().getUsedTimes().map(c->c.v()).orElse(0);
                	return ChildCareNurseErrorsExport.of(ChildCareNurseUsedNumberExport.of(days, Optional.of(times)), i.getLimitDays(), i.getYmd());
                })
                .collect(Collectors.toList()),

                //importData.getAsOfPeriodEnd(),
                ChildCareNurseUsedNumberExport.of(
                		importData.getAsOfPeriodEnd().getUsedDay().v(),
                		importData.getAsOfPeriodEnd().getUsedTimes().map(c->c.v())),


                //ChildCareNurseStartdateDaysInfo

                ChildCareNurseStartdateDaysInfoExport.of(
//                		ChildCareNurseStartdateInfoExport.of(
//                                importData.getStartdateDays().getThisYear().getUsedDays(),
//                                ChildCareNurseRemainingNumberExport.of(
//                                        importData.getStartdateDays().getThisYear().getRemainingNumber().getUsedDays(),
//                                        importData.getStartdateDays().getThisYear().getRemainingNumber().getUsedTime()
//                                ),
//                                importData.getStartdateDays().getThisYear().getLimitDays()
//                        ),
                		null,
//                        importData.getStartdateDays().getNextYear().map(i -> ChildCareNurseStartdateInfoExport.of(
//                                i.getUsedDays(),
//                                ChildCareNurseRemainingNumberExport.of(
//                                        i.getRemainingNumber().getUsedDays(),
//                                        i.getRemainingNumber().getUsedTime()
//                                ),
//                                i.getLimitDays()
//                        ))
                		null
                ),

                importData.isStartDateAtr(),


//                ChildCareNurseAggrPeriodDaysInfoExport.of(
//                        ChildCareNurseAggrPeriodInfoExport.of(
//                                importData.getAggrperiodinfo().getThisYear().getUsedCount(),
//                                importData.getAggrperiodinfo().getThisYear().getUsedDays(),
//                                importData.getAggrperiodinfo().getThisYear().getAggrPeriodUsedNumber()
//                        ),
//                        importData.getAggrperiodinfo().getNextYear().map(i -> ChildCareNurseAggrPeriodInfoExport.of(
//                                i.getUsedCount(),
//                                i.getUsedDays(),
//                                i.getAggrPeriodUsedNumber()
//                        ))
                null
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
