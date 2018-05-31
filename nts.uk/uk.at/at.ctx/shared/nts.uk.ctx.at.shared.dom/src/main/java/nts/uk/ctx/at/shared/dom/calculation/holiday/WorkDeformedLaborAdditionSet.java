/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.calculation.holiday;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.calculation.holiday.kmk013_splitdomain.DeductLeaveEarly;
import nts.uk.ctx.at.shared.dom.calculation.holiday.kmk013_splitdomain.EmploymentCalcDetailedSetIncludeVacationAmount;
import nts.uk.ctx.at.shared.dom.calculation.holiday.kmk013_splitdomain.HolidayCalcMethodSet;
import nts.uk.ctx.at.shared.dom.calculation.holiday.kmk013_splitdomain.IncludeHolidaysPremiumCalcDetailSet;
import nts.uk.ctx.at.shared.dom.calculation.holiday.kmk013_splitdomain.PremiumCalcMethodDetailOfHoliday;
import nts.uk.ctx.at.shared.dom.calculation.holiday.kmk013_splitdomain.PremiumHolidayCalcMethod;
import nts.uk.ctx.at.shared.dom.calculation.holiday.kmk013_splitdomain.WorkTimeCalcMethodDetailOfHoliday;
import nts.uk.ctx.at.shared.dom.calculation.holiday.kmk013_splitdomain.WorkTimeHolidayCalcMethod;

/**
 * The Class WorkDeformedLaborAdditionSet.
 */
@NoArgsConstructor
@Getter
// 変形労働勤務の加算設定
public class WorkDeformedLaborAdditionSet extends AggregateRoot{
	/** 会社ID */
	private String companyId;
	
	// 休暇の計算方法の設定
	private HolidayCalcMethodSet vacationCalcMethodSet;

	/**
	 * Creates the from java type.
	 *
	 * @param companyId the company id
	 * @param calcActualOperation1 the calc actual operation 1
	 * @param exemptTaxTime1 the exempt tax time 1
	 * @param incChildNursingCare1 the inc child nursing care 1
	 * @param additionTime1 the addition time 1
	 * @param notDeductLateleave1 the not deduct lateleave 1
	 * @param deformatExcValue the deformat exc value
	 * @param exemptTaxTime2 the exempt tax time 2
	 * @param minusAbsenceTime2 the minus absence time 2
	 * @param calcActualOperation2 the calc actual operation 2
	 * @param incChildNursingCare2 the inc child nursing care 2
	 * @param notDeductLateleave2 the not deduct lateleave 2
	 * @param additionTime2 the addition time 2
	 * @param enableSetPerWorkHour1 the enable set per work hour 1
	 * @param enableSetPerWorkHour2 the enable set per work hour 2
	 * @return the work deformed labor addition set
	 */
	public static WorkDeformedLaborAdditionSet createFromJavaType(String companyId, int calcActualOperation1, int exemptTaxTime1,
																	int incChildNursingCare1, int additionTime1, 
																	int notDeductLateleave1, int deformatExcValue,
																	int exemptTaxTime2, int minusAbsenceTime2, 
																	int calcActualOperation2, int incChildNursingCare2,
																	int notDeductLateleave2, int additionTime2, 
																	int enableSetPerWorkHour1) {
		return new WorkDeformedLaborAdditionSet(companyId, calcActualOperation1,
				exemptTaxTime1, incChildNursingCare1, additionTime1, notDeductLateleave1,
				deformatExcValue, exemptTaxTime2, minusAbsenceTime2,
				calcActualOperation2, incChildNursingCare2,
				notDeductLateleave2, additionTime2,
				enableSetPerWorkHour1);
	}

	/**
	 * Instantiates a new work deformed labor addition set.
	 *
	 * @param companyId the company id
	 * @param calcActualOperation1 the calc actual operation 1
	 * @param exemptTaxTime1 the exempt tax time 1
	 * @param incChildNursingCare1 the inc child nursing care 1
	 * @param additionTime1 the addition time 1
	 * @param notDeductLateleave1 the not deduct lateleave 1
	 * @param deformatExcValue the deformat exc value
	 * @param exemptTaxTime2 the exempt tax time 2
	 * @param minusAbsenceTime2 the minus absence time 2
	 * @param calcActualOperation2 the calc actual operation 2
	 * @param incChildNursingCare2 the inc child nursing care 2
	 * @param notDeductLateleave2 the not deduct lateleave 2
	 * @param additionTime2 the addition time 2
	 * @param enableSetPerWorkHour1 the enable set per work hour 1
	 * @param enableSetPerWorkHour2 the enable set per work hour 2
	 */
	public WorkDeformedLaborAdditionSet(String companyId, int calcActualOperation1,
			int exemptTaxTime1, int incChildNursingCare1, int additionTime1, int notDeductLateleave1,
			int deformatExcValue, int exemptTaxTime2, int minusAbsenceTime2,
			int calcActualOperation2, int incChildNursingCare2, int notDeductLateleave2,
			int additionTime2, int enableSetPerWorkHour1) {
		super();
		this.companyId = companyId;
		IncludeHolidaysPremiumCalcDetailSet includeHolidaysPremiumCalcDetailSet = new IncludeHolidaysPremiumCalcDetailSet(additionTime1, deformatExcValue, null);
		DeductLeaveEarly deductLeaveEarly = new DeductLeaveEarly(notDeductLateleave1, enableSetPerWorkHour1);
		PremiumCalcMethodDetailOfHoliday advanceSetPre = new PremiumCalcMethodDetailOfHoliday(includeHolidaysPremiumCalcDetailSet, incChildNursingCare1, 
																							deductLeaveEarly, exemptTaxTime1);
		PremiumHolidayCalcMethod premiumHolidayCalcMethod = new PremiumHolidayCalcMethod(calcActualOperation1, advanceSetPre);
		
		
		EmploymentCalcDetailedSetIncludeVacationAmount includeVacationSet 
																	= new EmploymentCalcDetailedSetIncludeVacationAmount(additionTime2, null, 
																														null, 
																														null);
		WorkTimeCalcMethodDetailOfHoliday advanceSetWork = new WorkTimeCalcMethodDetailOfHoliday(includeVacationSet, incChildNursingCare2, 
																									notDeductLateleave2, 
																									exemptTaxTime2, minusAbsenceTime2);
		WorkTimeHolidayCalcMethod workTimeHolidayCalcMethod = new WorkTimeHolidayCalcMethod(calcActualOperation2, advanceSetWork);
		
		HolidayCalcMethodSet calcMethodSet = new HolidayCalcMethodSet(premiumHolidayCalcMethod, workTimeHolidayCalcMethod);
		
		this.vacationCalcMethodSet = calcMethodSet;
	}
}
