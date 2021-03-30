package nts.uk.ctx.at.request.ac.record.monthly.vacation.childcarenurse;

import nts.uk.ctx.at.record.pub.monthly.vacation.childcarenurse.childcare.*;
import nts.uk.ctx.at.request.dom.adapter.monthly.vacation.childcarenurse.*;
import java.util.stream.Collectors;

public class ImportedClassConverter {

	static public ChildCareNursePeriodImport exportToImport(ChildCareNursePeriodExport export) {
        return new ChildCareNursePeriodImport(

        		/** エラー情報 */
        		export.getChildCareNurseErrors().stream()
        			.map(i -> ChildCareNurseErrorsImport.of(
        				ChildCareNurseUsedNumberImport.of(
        						i.getUsedNumber().getUsedDays(),
        						i.getUsedNumber().getUsedTime()),
        				i.getLimitDays(),
        				i.getYmd()))
    			.collect(Collectors.toList()),

    			/** 期間終了日の翌日時点での使用数 */
        		ChildCareNurseUsedNumberImport.of(
        				export.getAsOfPeriodEnd().getUsedDays(),
        				export.getAsOfPeriodEnd().getUsedTime()),

        		/** 起算日からの休暇情報 */
                ChildCareNurseStartdateDaysInfoImport.of(
                        ChildCareNurseStartdateInfoImport.of(
                        		ChildCareNurseUsedNumberImport.of(
                        				export.getStartdateDays().getThisYear().getUsedDays().getUsedDays(),
                        				export.getStartdateDays().getThisYear().getUsedDays().getUsedTime()
                        				),
                                ChildCareNurseRemainingNumberImport.of(
                                        export.getStartdateDays().getThisYear().getRemainingNumber().getUsedDays(),
                                        export.getStartdateDays().getThisYear().getRemainingNumber().getUsedTime()
                                ),
                                export.getStartdateDays().getThisYear().getLimitDays()
                        ),
                        export.getStartdateDays().getNextYear().map(i -> ChildCareNurseStartdateInfoImport.of(
                        		ChildCareNurseUsedNumberImport.of(
                        				i.getUsedDays().getUsedDays(),
                        				i.getUsedDays().getUsedTime()
                        				),
                                ChildCareNurseRemainingNumberImport.of(
                                        i.getRemainingNumber().getUsedDays(),
                                        i.getRemainingNumber().getUsedTime()
                                ),
                                i.getLimitDays()
                        ))
                ),

                /** 起算日を含む期間フラグ */
                export.isStartDateAtr(),

                /** 集計期間の休暇情報*/
                ChildCareNurseAggrPeriodDaysInfoImport.of(
                        ChildCareNurseAggrPeriodInfoImport.of(
                                export.getAggrperiodinfo().getThisYear().getUsedCount(),
                                export.getAggrperiodinfo().getThisYear().getUsedDays(),
                                ChildCareNurseUsedNumberImport.of(
                                		export.getAggrperiodinfo().getThisYear().getAggrPeriodUsedNumber().getUsedDays(),
                                		export.getAggrperiodinfo().getThisYear().getAggrPeriodUsedNumber().getUsedTime()
                                		)
                        ),
                        export.getAggrperiodinfo().getNextYear()
                        		.map(i -> ChildCareNurseAggrPeriodInfoImport.of(
	                                i.getUsedCount(),
	                                i.getUsedDays(),
	                                ChildCareNurseUsedNumberImport.of(
	                                		i.getAggrPeriodUsedNumber().getUsedDays(),
	                                		i.getAggrPeriodUsedNumber().getUsedTime()
	                                		)
	                                ))
                )
        );
    }

	static public ChildCareNursePeriodExport importToExport(ChildCareNursePeriodImport imp) {
    	return new ChildCareNursePeriodExport(

        		/** エラー情報 */
    			imp.getChildCareNurseErrors().stream()
        			.map(i -> ChildCareNurseErrorsExport.of(
        				ChildCareNurseUsedNumberExport.of(
        						i.getUsedNumber().getUsedDays(),
        						i.getUsedNumber().getUsedTime()),
        				i.getLimitDays(),
        				i.getYmd()))
    			.collect(Collectors.toList()),

    			/** 期間終了日の翌日時点での使用数 */
        		ChildCareNurseUsedNumberExport.of(
        				imp.getAsOfPeriodEnd().getUsedDays(),
        				imp.getAsOfPeriodEnd().getUsedTime()),

        		/** 起算日からの休暇情報 */
                ChildCareNurseStartdateDaysInfoExport.of(
                        ChildCareNurseStartdateInfoExport.of(
                        		ChildCareNurseUsedNumberExport.of(
                        				imp.getStartdateDays().getThisYear().getUsedDays().getUsedDays(),
                        				imp.getStartdateDays().getThisYear().getUsedDays().getUsedTime()
                        				),
                                ChildCareNurseRemainingNumberExport.of(
                                		imp.getStartdateDays().getThisYear().getRemainingNumber().getUsedDays(),
                                		imp.getStartdateDays().getThisYear().getRemainingNumber().getUsedTime()
                                ),
                                imp.getStartdateDays().getThisYear().getLimitDays()
                        ),
                        imp.getStartdateDays().getNextYear().map(i -> ChildCareNurseStartdateInfoExport.of(
                        		ChildCareNurseUsedNumberExport.of(
                        				i.getUsedDays().getUsedDays(),
                        				i.getUsedDays().getUsedTime()
                        				),
                                ChildCareNurseRemainingNumberExport.of(
                                        i.getRemainingNumber().getUsedDays(),
                                        i.getRemainingNumber().getUsedTime()
                                ),
                                i.getLimitDays()
                        ))
                ),

                /** 起算日を含む期間フラグ */
                imp.isStartDateAtr(),

                /** 集計期間の休暇情報*/
                ChildCareNurseAggrPeriodDaysInfoExport.of(
                        ChildCareNurseAggrPeriodInfoExport.of(
                        		imp.getAggrperiodinfo().getThisYear().getUsedCount(),
                        		imp.getAggrperiodinfo().getThisYear().getUsedDays(),
                                ChildCareNurseUsedNumberExport.of(
                                		imp.getAggrperiodinfo().getThisYear().getAggrPeriodUsedNumber().getUsedDays(),
                                		imp.getAggrperiodinfo().getThisYear().getAggrPeriodUsedNumber().getUsedTime()
                                		)
                        ),
                        imp.getAggrperiodinfo().getNextYear()
                        		.map(i -> ChildCareNurseAggrPeriodInfoExport.of(
	                                i.getUsedCount(),
	                                i.getUsedDays(),
	                                ChildCareNurseUsedNumberExport.of(
	                                		i.getAggrPeriodUsedNumber().getUsedDays(),
	                                		i.getAggrPeriodUsedNumber().getUsedTime()
	                                		)
	                                ))
                )
        );
    }

    static public TmpChildCareNurseMngWorkExport tmpChildCareNurseMngWorkToExport(TmpChildCareNurseMngWorkImport importData) {
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