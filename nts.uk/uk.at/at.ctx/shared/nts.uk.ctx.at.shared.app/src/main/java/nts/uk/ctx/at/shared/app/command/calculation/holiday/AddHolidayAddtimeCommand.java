/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.calculation.holiday;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.AddSetManageWorkHour;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HolidayAddtionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HourlyPaymentAdditionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.LeaveSetAdded;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.ReferEmployeeInformation;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.ReferWorkRecord;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.ReferenceDestinationAbsenceWorkingHours;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.TimeHolidayAddingMethod;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.TimeHolidayAdditionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.VacationSpecifiedTimeRefer;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.WorkClassOfTimeHolidaySet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.WorkDeformedLaborAdditionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.WorkFlexAdditionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.WorkRegularAdditionSet;
import nts.uk.ctx.at.shared.dom.workingcondition.BreakdownTimeDay;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * The Class AddHolidayAddtimeCommand.
 */

/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Data

/**
 * Instantiates a new adds the holiday addtime command.
 *
 * @param referComHolidayTime the refer com holiday time
 * @param oneDay the one day
 * @param morning the morning
 * @param afternoon the afternoon
 * @param referActualWorkHours the refer actual work hours
 * @param notReferringAch the not referring ach
 * @param annualHoliday the annual holiday
 * @param specialHoliday the special holiday
 * @param yearlyReserved the yearly reserved
 * @param regularWork the regular work
 * @param flexWork the flex work
 * @param irregularWork the irregular work
 * @param addSetManageWorkHour the add set manage work hour
 * @param hourlyPaymentAddCommand the hourly payment add command
 * @param addingMethod1 the adding method 1
 * @param workClass1 the work class 1
 * @param addingMethod2 the adding method 2
 * @param workClass2 the work class 2
 */
@AllArgsConstructor
public class AddHolidayAddtimeCommand {

	/** The refer com holiday time. */
	private int referComHolidayTime;

	/** The one day. */
	private BigDecimal oneDay;

	/** The morning. */
	private BigDecimal morning;

	/** The afternoon. */
	private BigDecimal afternoon;

	/** The refer actual work hours. */
	private int referActualWorkHours;

	/** The not referring ach. */
	private int notReferringAch;

	/** The annual holiday. */
	private int annualHoliday;

	/** The special holiday. */
	private int specialHoliday;

	/** The yearly reserved. */
	private int yearlyReserved;

	/** The regular work. */
	private RegularWorkCommand regularWork;

	/** The flex work. */
	private FlexWorkCommand flexWork;

	/** The irregular work. */
	private WorkDepLaborCommand irregularWork;
	
	/** The add set manage work hour. */
	/*時間外超過の加算設定*/
	private int addSetManageWorkHour;
	
	/** The hourly payment add command. */
	/*時給者の加算設定*/
	private HourlyPaymentAdditionSetCommand hourlyPaymentAddCommand;
		
	/** The adding method 1. */
	/*加算方法*/
	private int addingMethod1;
	
	/** The work class 1. */
	/*勤務区分*/
	private int workClass1;
	
	/** The adding method 2. */
	/*加算方法*/
	private int addingMethod2;
	
	/** The work class 2. */
	/*勤務区分*/
	private int workClass2;	

	
	/**
	 * To domain.
	 *
	 * @param companyId the company id
	 * @return the map
	 */
	public Map<String, AggregateRoot> toDomain(String companyId) {
		BreakdownTimeDay breakdownTimeDay = new BreakdownTimeDay(new AttendanceTime(this.oneDay.intValue()),
				new AttendanceTime(this.morning.intValue()),
				new AttendanceTime(this.afternoon.intValue()));
		
		ReferenceDestinationAbsenceWorkingHours workingHours = ReferenceDestinationAbsenceWorkingHours.valueOf(this.referComHolidayTime); 
		
		ReferWorkRecord referWorkRecord = new ReferWorkRecord(workingHours, breakdownTimeDay);
		
		VacationSpecifiedTimeRefer vacationSpecifiedTimeRefer = VacationSpecifiedTimeRefer.valueOf(this.notReferringAch);
		ReferEmployeeInformation referEmployeeInformation = new ReferEmployeeInformation(vacationSpecifiedTimeRefer);
		
		LeaveSetAdded additionVacationSet = new LeaveSetAdded(NotUseAtr.valueOf(this.annualHoliday), 
																NotUseAtr.valueOf(this.yearlyReserved), 
																NotUseAtr.valueOf(this.specialHoliday));
		
		// convert command to domain
		WorkRegularAdditionSet workRegularAdditionSet = this.toDomainRegularWork(companyId);
		WorkFlexAdditionSet flexAdditionSet = this.toDomainFlexWork(companyId);
		WorkDeformedLaborAdditionSet deformedLaborAdditionSet = this.toDomainIrregularWork(companyId);
		AddSetManageWorkHour addSetManageWorkHour = this.toDomainAddSetManageWorkHour(companyId);
		HourlyPaymentAdditionSet hourlyPaymentAdditionSet = this.todomainHourlyPaymentAdd(companyId);
		
		// convert to domain
		HolidayAddtionSet holidayAddtionSet = HolidayAddtionSet.createFromJavaType(companyId, 
				referWorkRecord,
				NotUseAtr.valueOf(this.referActualWorkHours), 
				referEmployeeInformation,
				additionVacationSet,
				createLstTimeHolidayAdditionSet());
		
		Map<String, AggregateRoot> mapAggre = new HashMap<>();
		
		mapAggre.put("holidayAddtionSet", holidayAddtionSet);
		mapAggre.put("regularWork", workRegularAdditionSet);
		mapAggre.put("flexWork", flexAdditionSet);
		mapAggre.put("irregularWork", deformedLaborAdditionSet);
		mapAggre.put("hourlyPaymentAdditionSet", hourlyPaymentAdditionSet);
		mapAggre.put("addSetManageWorkHour", addSetManageWorkHour);
		
		return mapAggre;
	}

	/**
	 * Creates the lst time holiday addition set.
	 *
	 * @return the list
	 */
	private List<TimeHolidayAdditionSet> createLstTimeHolidayAdditionSet() {
		List<TimeHolidayAdditionSet> lstTimeHDAddSet = new ArrayList<>();
		lstTimeHDAddSet.add(TimeHolidayAdditionSet.builder().addingMethod(TimeHolidayAddingMethod.valueOf(addingMethod1)).workClass(WorkClassOfTimeHolidaySet.valueOf(workClass1)).build());
		lstTimeHDAddSet.add(TimeHolidayAdditionSet.builder().addingMethod(TimeHolidayAddingMethod.valueOf(addingMethod2)).workClass(WorkClassOfTimeHolidaySet.valueOf(workClass2)).build());
		return lstTimeHDAddSet;
	}
	
	/**
	 * To domain add set manage work hour.
	 *
	 * @param companyId the company id
	 * @return the adds the set manage work hour
	 */
	private AddSetManageWorkHour toDomainAddSetManageWorkHour(String companyId) {
		AddSetManageWorkHour addSetManageWorkHour = AddSetManageWorkHour.builder()
														.companyId(companyId)
														.additionSettingOfOvertime(NotUseAtr.valueOf(this.addSetManageWorkHour)).build();
		return addSetManageWorkHour;
	}
	
	/**
	 * To domain regular work.
	 *
	 * @param companyId the company id
	 * @return the work regular addition set
	 */
	private WorkRegularAdditionSet toDomainRegularWork(String companyId) {
		if (this.regularWork == null) {
			return null;
		}
		return WorkRegularAdditionSet.createFromJavaType(companyId, this.regularWork.getCalcActualOperationPre(),
				this.regularWork.getExemptTaxTimePre(), this.regularWork.getIncChildNursingCarePre(),
				this.regularWork.getAdditionTimePre(), this.regularWork.getNotDeductLateleavePre(),
				this.regularWork.getDeformatExcValuePre(), this.regularWork.getExemptTaxTimeWork(),
				this.regularWork.getCalcActualOperationWork(), this.regularWork.getIncChildNursingCareWork(),
				this.regularWork.getNotDeductLateleaveWork(), this.regularWork.getAdditionTimeWork(),
				this.regularWork.getEnableSetPerWorkHour1());
	}

	/**
	 * To domain flex work.
	 *
	 * @param companyId the company id
	 * @return the work flex addition set
	 */
	private WorkFlexAdditionSet toDomainFlexWork(String companyId) {
		if (this.flexWork == null) {
			return null;
		}
		return WorkFlexAdditionSet.createFromJavaType(companyId, this.flexWork.getCalcActualOperationPre(),
				this.flexWork.getExemptTaxTimePre(), this.flexWork.getIncChildNursingCarePre(),
				this.flexWork.getPredeterminedOvertimePre(), this.flexWork.getAdditionTimePre(),
				this.flexWork.getNotDeductLateleavePre(), this.flexWork.getExemptTaxTimeWork(),
				this.flexWork.getMinusAbsenceTimeWork(), this.flexWork.getCalcActualOperationWork(),
				this.flexWork.getIncChildNursingCareWork(), this.flexWork.getNotDeductLateleaveWork(),
				this.flexWork.getPredeterminDeficiencyWork(), this.flexWork.getAdditionTimeWork(),
				this.flexWork.getEnableSetPerWorkHour1(),
				this.flexWork.getAdditionWithinMonthlyStatutory());
	}

	/**
	 * To domain irregular work.
	 *
	 * @param companyId the company id
	 * @return the work deformed labor addition set
	 */
	private WorkDeformedLaborAdditionSet toDomainIrregularWork(String companyId) {
		if (this.irregularWork == null) {
			return null;
		}
		return WorkDeformedLaborAdditionSet.createFromJavaType(companyId, this.irregularWork.getCalcActualOperationPre(),
				this.irregularWork.getExemptTaxTimePre(), this.irregularWork.getIncChildNursingCarePre(), this.irregularWork.getAdditionTimePre(),
				this.irregularWork.getNotDeductLateleavePre(),this.irregularWork.getDeformatExcValue(), this.irregularWork.getExemptTaxTimeWork(),
				this.irregularWork.getMinusAbsenceTimeWork(), this.irregularWork.getCalcActualOperationWork(),
				this.irregularWork.getIncChildNursingCareWork(), this.irregularWork.getNotDeductLateleaveWork(),this.irregularWork.getAdditionTimeWork(),
				this.irregularWork.getEnableSetPerWorkHour1());
	}
	
	/**
	 * Todomain hourly payment add.
	 *
	 * @param companyId the company id
	 * @return the hourly payment addition set
	 */
	private HourlyPaymentAdditionSet todomainHourlyPaymentAdd(String companyId) {
		if (this.hourlyPaymentAddCommand == null) {
			return null;
		}
		return HourlyPaymentAdditionSet.createFromJavaType(companyId, this.hourlyPaymentAddCommand.getCalcPremiumVacation(), this.hourlyPaymentAddCommand.getAddition1(), 
				this.hourlyPaymentAddCommand.getDeformatExcValue(), this.hourlyPaymentAddCommand.getIncChildNursingCare(), this.hourlyPaymentAddCommand.getDeduct(), 
				this.hourlyPaymentAddCommand.getCalculateIncludeIntervalExemptionTime1(), 
				this.hourlyPaymentAddCommand.getCalcWorkHourVacation() , this.hourlyPaymentAddCommand.getAddition2(), this.hourlyPaymentAddCommand.getCalculateIncludCareTime(), 
				this.hourlyPaymentAddCommand.getNotDeductLateLeaveEarly(), 
				this.hourlyPaymentAddCommand.getCalculateIncludeIntervalExemptionTime2(), this.hourlyPaymentAddCommand.getEnableSetPerWorkHour1());
	}
}
