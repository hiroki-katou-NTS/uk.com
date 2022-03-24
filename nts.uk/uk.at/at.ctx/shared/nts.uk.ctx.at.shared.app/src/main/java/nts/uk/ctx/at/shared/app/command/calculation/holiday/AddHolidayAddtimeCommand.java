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
import nts.uk.ctx.at.shared.dom.common.time.BreakDownTimeDay;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.AddSetManageWorkHour;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HolidayAddtionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HourlyPaymentAdditionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.LeaveSetAdded;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.RefDesForAdditionalTakeLeave;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.TimeHolidayAddingMethod;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.TimeHolidayAdditionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.WorkClassOfTimeHolidaySet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.WorkDeformedLaborAdditionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.WorkFlexAdditionSet;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.WorkRegularAdditionSet;
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

	/** The one day. */
	private BigDecimal oneDay;

	/** The morning. */
	private BigDecimal morning;

	/** The afternoon. */
	private BigDecimal afternoon;

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

	// 参照先設定
	private int refAtrCom;

	// 個人別設定参照先
	private Integer refAtrEmp;

	
	/**
	 * To domain.
	 *
	 * @param companyId the company id
	 * @return the map
	 */
	public Map<String, AggregateRoot> toDomain(String companyId) {
		BreakDownTimeDay breakdownTimeDay = new BreakDownTimeDay(new AttendanceTime(this.oneDay.intValue()),
				new AttendanceTime(this.morning.intValue()),
				new AttendanceTime(this.afternoon.intValue()));
		
		LeaveSetAdded additionVacationSet = new LeaveSetAdded(NotUseAtr.valueOf(this.annualHoliday), 
																NotUseAtr.valueOf(this.yearlyReserved), 
																NotUseAtr.valueOf(this.specialHoliday));
		
		// convert command to domain
		WorkRegularAdditionSet workRegularAdditionSet = this.toDomainRegularWork(companyId);
		WorkFlexAdditionSet flexAdditionSet = this.toDomainFlexWork(companyId);
		WorkDeformedLaborAdditionSet deformedLaborAdditionSet = this.toDomainIrregularWork(companyId);
		AddSetManageWorkHour addSetManageWorkHour = this.toDomainAddSetManageWorkHour(companyId);
		HourlyPaymentAdditionSet hourlyPaymentAdditionSet = this.todomainHourlyPaymentAdd(companyId);

		RefDesForAdditionalTakeLeave refDesForAdditionalTakeLeave = new RefDesForAdditionalTakeLeave(
				breakdownTimeDay,
				this.refAtrCom,
				this.refAtrEmp
		);
		
		// convert to domain
		HolidayAddtionSet holidayAddtionSet = HolidayAddtionSet.createFromJavaType(
				companyId,
				refDesForAdditionalTakeLeave,
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
		return WorkRegularAdditionSet.createFromJavaType(
				companyId,
				this.regularWork.getCalcActualOperationPre(),
				this.regularWork.getIncChildNursingCarePre(),
				this.regularWork.getNotDeductLateleavePre(),
				0,
				this.regularWork.getEnableSetPerWorkHour1(),
				this.regularWork.getExemptTaxTimePre(),
				this.regularWork.getAdditionTimePre(),
				this.regularWork.getDeformatExcValuePre(),
				0,
				this.regularWork.getCalcActualOperationWork(),
				this.regularWork.getIncChildNursingCareWork(),
				this.regularWork.getNotDeductLateleaveWork(),
				0,
				this.regularWork.getEnableSetPerWorkHour2(),
				this.regularWork.getExemptTaxTimeWork(),
				this.regularWork.getAdditionTimeWork(),
				0,
				0,
				0,
				0,
				this.regularWork.getUseAtr());
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
		return WorkFlexAdditionSet.createFromJavaType(
				companyId,
				this.flexWork.getCalcActualOperationPre(),
				this.flexWork.getIncChildNursingCarePre(),
				this.flexWork.getNotDeductLateleavePre(),
				0,
				this.flexWork.getEnableSetPerWorkHour1(),
				this.flexWork.getExemptTaxTimePre(),
				this.flexWork.getAdditionTimePre(),
				0,
				this.flexWork.getPredeterminedOvertimePre(),
				this.flexWork.getCalcActualOperationWork(),
				this.flexWork.getIncChildNursingCareWork(),
				this.flexWork.getNotDeductLateleaveWork(),
				0,
				this.flexWork.getEnableSetPerWorkHour2(),
				this.flexWork.getExemptTaxTimeWork(),
				this.flexWork.getAdditionTimeWork(),
				0,
				this.flexWork.getPredeterminDeficiencyWork(),
				this.flexWork.getAdditionWithinMonthlyStatutory(),
				this.flexWork.getMinusAbsenceTimeWork(),
				this.flexWork.getUseAtr());
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
		return WorkDeformedLaborAdditionSet.createFromJavaType(
				companyId,
				this.irregularWork.getCalcActualOperationPre(),
				this.irregularWork.getIncChildNursingCarePre(),
				this.irregularWork.getNotDeductLateleavePre(),
				0,
				this.irregularWork.getEnableSetPerWorkHour1(),
				this.irregularWork.getExemptTaxTimePre(),
				this.irregularWork.getAdditionTimePre(),
				this.irregularWork.getDeformatExcValue(),
				this.irregularWork.getPredeterminedOvertimePre(),
				this.irregularWork.getCalcActualOperationWork(),
				this.irregularWork.getIncChildNursingCareWork(),
				this.irregularWork.getNotDeductLateleaveWork(),
				0,
				this.irregularWork.getEnableSetPerWorkHour2(),
				this.irregularWork.getExemptTaxTimeWork(),
				this.irregularWork.getAdditionTimeWork(),
				0,
				this.irregularWork.getPredeterminDeficiencyWork(),
				0,
				this.irregularWork.getMinusAbsenceTimeWork(),
				this.irregularWork.getUseAtr());
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
		return HourlyPaymentAdditionSet.createFromJavaType(
				companyId,
				this.hourlyPaymentAddCommand.getCalcPremiumVacation(),
				this.hourlyPaymentAddCommand.getIncChildNursingCare(),
				this.hourlyPaymentAddCommand.getDeduct(),
				0,
				this.hourlyPaymentAddCommand.getEnableSetPerWorkHour1(),
				this.hourlyPaymentAddCommand.getCalculateIncludeIntervalExemptionTime1(),
				this.hourlyPaymentAddCommand.getAddition1(),
				this.hourlyPaymentAddCommand.getDeformatExcValue(),
				0,
				this.hourlyPaymentAddCommand.getCalcWorkHourVacation(),
				this.hourlyPaymentAddCommand.getCalculateIncludCareTime(),
				this.hourlyPaymentAddCommand.getNotDeductLateLeaveEarly(),
				0,
				this.hourlyPaymentAddCommand.getEnableSetPerWorkHour2(),
				this.hourlyPaymentAddCommand.getCalculateIncludeIntervalExemptionTime2(),
				this.hourlyPaymentAddCommand.getAddition2(),
				0,
				0,
				0,
				0,
				this.hourlyPaymentAddCommand.getUseAtr());
	}
}
