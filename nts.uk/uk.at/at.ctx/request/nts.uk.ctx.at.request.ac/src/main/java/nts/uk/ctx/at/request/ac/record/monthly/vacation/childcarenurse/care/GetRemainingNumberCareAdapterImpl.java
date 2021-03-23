package nts.uk.ctx.at.request.ac.record.monthly.vacation.childcarenurse.care;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.pub.monthly.vacation.childcarenurse.childcare.*;
import nts.uk.ctx.at.request.dom.adapter.monthly.vacation.childcarenurse.*;
import nts.uk.ctx.at.request.dom.adapter.monthly.vacation.childcarenurse.care.GetRemainingNumberCareAdapter;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.InterimRemainMngMode;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.ChildCareNurseUsedNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.DayNumberOfUse;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.TimeOfUse;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class GetRemainingNumberCareAdapterImpl implements GetRemainingNumberCareAdapter {
    @Inject
    private GetRemainingNumberCarePub publisher;

    @Override
    public ChildCareNursePeriodImport getCareRemNumWithinPeriod(
            String employeeId,
            DatePeriod period,
            InterimRemainMngMode performReferenceAtr,
            GeneralDate criteriaDate,
            Optional<Boolean> isOverWrite,
            List<TmpChildCareNurseMngWorkImport> tempCareDataforOverWriteList,
            Optional<ChildCareNursePeriodImport> prevCareLeave,
            Optional<CreateAtr> createAtr,
            Optional<DatePeriod> periodOverWrite) {

        List< TmpChildCareNurseMngWorkExport > tmp =
        		tempCareDataforOverWriteList.stream().map(this::tmpChildCareNurseMngWorkToExport).collect(Collectors.toList());

        ChildCareNursePeriodExport result = publisher.getCareRemNumWithinPeriod(
        		AppContexts.user().companyId(),
                employeeId,
                period,
                performReferenceAtr,
                criteriaDate,
                isOverWrite,
                tmp,
                prevCareLeave.map(this::importToExport),
                createAtr,
                periodOverWrite);
        return exportToImport(result);
    }

    private ChildCareNursePeriodImport exportToImport(ChildCareNursePeriodExport export) {
        return new ChildCareNursePeriodImport(

        		export.getChildCareNurseErrors().stream().map(
                			i -> ChildCareNurseErrorsImport.of(
                				ChildCareNurseUsedNumber.of(
                						new DayNumberOfUse(i.getUsedNumber().getUsedDay()),
                						Optional.of(new TimeOfUse(
                								i.getUsedNumber().getUsedTimes().isPresent() ?
                										i.getUsedNumber().getUsedTimes().get() : 0 ))
                						),
                				 i.getLimitDays(),
                				 i.getYmd())
                		).collect(Collectors.toList()),

                export.getAsOfPeriodEnd(),
//                null,





                ChildCareNurseStartdateDaysInfoImport.of(
                        ChildCareNurseStartdateInfoImport.of(
                        		ChildCareNurseUsedNumber.of(
                        				// 要修正
                        				//new DayNumberOfUse(export.getStartdateDays().getThisYear().getUsedDays()),
                        				null,



                        				Optional.of(new TimeOfUse(
                        						export.getStartdateDays().getThisYear().getRemainingNumber().getUsedTime().isPresent()?
                        								export.getStartdateDays().getThisYear().getRemainingNumber().getUsedTime().get(): 0))),

                                ChildCareNurseRemainingNumberImport.of(
                                        export.getStartdateDays().getThisYear().getRemainingNumber().getUsedDays(),
                                        export.getStartdateDays().getThisYear().getRemainingNumber().getUsedTime()
                                ),
                                export.getStartdateDays().getThisYear().getLimitDays()
                        ),
                        export.getStartdateDays().getNextYear().map(i -> ChildCareNurseStartdateInfoImport.of(
                        		// 要修正
                                //i.getUsedDays(),
                        		null,




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
                                //export.getAggrperiodinfo().getThisYear().getAggrPeriodUsedNumber()
                                ChildCareNurseUsedNumber.of(
                                		new DayNumberOfUse(export.getAggrperiodinfo().getThisYear().getAggrPeriodUsedNumber().getUsedDay()),
                                		export.getAggrperiodinfo().getThisYear().getAggrPeriodUsedNumber().getUsedTimes().isPresent()?
                                				Optional.of(new TimeOfUse(export.getAggrperiodinfo().getThisYear().getAggrPeriodUsedNumber().getUsedTimes().get() ))
                                				: Optional.of(new TimeOfUse(0)))
                        ),
                        export.getAggrperiodinfo().getNextYear().map(i -> ChildCareNurseAggrPeriodInfoImport.of(
                                i.getUsedCount(),
                                i.getUsedDays(),
                             // 要修正
                                //i.getAggrPeriodUsedNumber())
                                null)





                                )

                )
        );
    }

    private ChildCareNursePeriodExport importToExport(ChildCareNursePeriodImport importData) {
        return new ChildCareNursePeriodExport(
                importData.getChildCareNurseErrors().stream().map(
                		i -> ChildCareNurseErrorsExport.of(
                				// 要修正
                				// i.getUsedNumber(),
                				null,




                				i.getLimitDays(),
                				i.getYmd())
                		).collect(Collectors.toList()),


                // importData.getAsOfPeriodEnd(),
                null,


//                ChildCareNurseStartdateDaysInfoExport.of(
//                        ChildCareNurseStartdateInfoExport.of(
//                                importData.getStartdateDays().getThisYear().getUsedDays(),
//                                ChildCareNurseRemainingNumberExport.of(
//                                        importData.getStartdateDays().getThisYear().getRemainingNumber().getUsedDays(),
//                                        importData.getStartdateDays().getThisYear().getRemainingNumber().getUsedTime()
//                                ),
//                                importData.getStartdateDays().getThisYear().getLimitDays()
//                        ),
//                        importData.getStartdateDays().getNextYear().map(i -> ChildCareNurseStartdateInfoExport.of(
//                                i.getUsedDays(),
//                                ChildCareNurseRemainingNumberExport.of(
//                                        i.getRemainingNumber().getUsedDays(),
//                                        i.getRemainingNumber().getUsedTime()
//                                ),
//                                i.getLimitDays()
//                        ))
//                ),
                null,


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
//                )
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
