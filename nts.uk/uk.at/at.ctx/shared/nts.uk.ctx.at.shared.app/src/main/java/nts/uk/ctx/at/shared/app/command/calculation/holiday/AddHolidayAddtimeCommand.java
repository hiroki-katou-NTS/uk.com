package nts.uk.ctx.at.shared.app.command.calculation.holiday;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.calculation.holiday.AddSetManageWorkHour;
import nts.uk.ctx.at.shared.dom.calculation.holiday.FlexWork;
import nts.uk.ctx.at.shared.dom.calculation.holiday.HolidayAddtion;
import nts.uk.ctx.at.shared.dom.calculation.holiday.HourlyPaymentAdditionSet;
import nts.uk.ctx.at.shared.dom.calculation.holiday.RegularWork;
import nts.uk.ctx.at.shared.dom.calculation.holiday.TimeHolidayAddingMethod;
import nts.uk.ctx.at.shared.dom.calculation.holiday.TimeHolidayAdditionSet;
import nts.uk.ctx.at.shared.dom.calculation.holiday.WorkClassOfTimeHolidaySet;
import nts.uk.ctx.at.shared.dom.calculation.holiday.WorkDepLabor;
import nts.uk.shr.com.enumcommon.NotUseAtr;
/**
 * 
 * @author phongtq
 *
 */
@Data
@AllArgsConstructor
public class AddHolidayAddtimeCommand {

	/** 会社単位の休暇時間を参照する */
	private int referComHolidayTime;

	/** Pre日 */
	private BigDecimal oneDay;

	/** 午前 */
	private BigDecimal morning;

	/** 午後 */
	private BigDecimal afternoon;

	/** 実績の就業時間帯を参照する */
	private int referActualWorkHours;

	/** 実績を参照しない場合の参照先 */
	private int notReferringAch;

	/** 年休 */
	private int annualHoliday;

	/** 特別休暇 */
	private int specialHoliday;

	/** 積立年休 */
	private int yearlyReserved;

	/** 通常勤務の加算設定 */
	private RegularWorkCommand regularWork;

	/** フレックス勤務の加算設定 */
	private FlexWorkCommand flexWork;

	/** 変形労働勤務の加算設定 */
	private WorkDepLaborCommand irregularWork;
	
	/*時間外超過の加算設定*/
	private int addSetManageWorkHour;
	
	/*時給者の加算設定*/
	private HourlyPaymentAdditionSetCommand hourlyPaymentAddCommand;
		
	/*加算方法*/
	private int addingMethod1;
	
	/*勤務区分*/
	private int workClass1;
	
	/*加算方法*/
	private int addingMethod2;
	
	/*勤務区分*/
	private int workClass2;	

	public HolidayAddtion toDomain(String companyId) {
		return HolidayAddtion.createFromJavaType(companyId, this.referComHolidayTime, this.oneDay, this.morning,
				this.afternoon, this.referActualWorkHours, this.notReferringAch, this.annualHoliday,
				this.specialHoliday, this.yearlyReserved, this.toDomainRegularWork(companyId),
				this.toDomainFlexWork(companyId), this.toDomainIrregularWork(companyId), this.toDomainAddSetManageWorkHour(companyId), 
				this.todomainHourlyPaymentAdd(companyId), createLstTimeHolidayAdditionSet());
	}

	private List<TimeHolidayAdditionSet> createLstTimeHolidayAdditionSet() {
		List<TimeHolidayAdditionSet> lstTimeHDAddSet = new ArrayList<>();
		lstTimeHDAddSet.add(TimeHolidayAdditionSet.builder().addingMethod(TimeHolidayAddingMethod.valueOf(addingMethod1)).workClass(WorkClassOfTimeHolidaySet.valueOf(workClass1)).build());
		lstTimeHDAddSet.add(TimeHolidayAdditionSet.builder().addingMethod(TimeHolidayAddingMethod.valueOf(addingMethod2)).workClass(WorkClassOfTimeHolidaySet.valueOf(workClass2)).build());
		return lstTimeHDAddSet;
	}
	
	private AddSetManageWorkHour toDomainAddSetManageWorkHour(String companyId) {
		AddSetManageWorkHour addSetManageWorkHour = AddSetManageWorkHour.builder()
														.companyId(companyId)
														.additionSettingOfOvertime(NotUseAtr.valueOf(this.addSetManageWorkHour)).build();
		return addSetManageWorkHour;
	}
	
	private RegularWork toDomainRegularWork(String companyId) {
		if (this.regularWork == null) {
			return null;
		}
		return RegularWork.createFromJavaType(companyId, this.regularWork.getCalcActualOperationPre(),
				this.regularWork.getExemptTaxTimePre(), this.regularWork.getIncChildNursingCarePre(),
				this.regularWork.getAdditionTimePre(), this.regularWork.getNotDeductLateleavePre(),
				this.regularWork.getDeformatExcValuePre(), this.regularWork.getExemptTaxTimeWork(),
				this.regularWork.getCalcActualOperationWork(), this.regularWork.getIncChildNursingCareWork(),
				this.regularWork.getNotDeductLateleaveWork(), this.regularWork.getAdditionTimeWork(),
				this.regularWork.getEnableSetPerWorkHour1(), this.regularWork.getEnableSetPerWorkHour2());
	}

	private FlexWork toDomainFlexWork(String companyId) {
		if (this.flexWork == null) {
			return null;
		}
		return FlexWork.createFromJavaType(companyId, this.flexWork.getCalcActualOperationPre(),
				this.flexWork.getExemptTaxTimePre(), this.flexWork.getIncChildNursingCarePre(),
				this.flexWork.getPredeterminedOvertimePre(), this.flexWork.getAdditionTimePre(),
				this.flexWork.getNotDeductLateleavePre(), this.flexWork.getExemptTaxTimeWork(),
				this.flexWork.getMinusAbsenceTimeWork(), this.flexWork.getCalcActualOperationWork(),
				this.flexWork.getIncChildNursingCareWork(), this.flexWork.getNotDeductLateleaveWork(),
				this.flexWork.getPredeterminDeficiencyWork(), this.flexWork.getAdditionTimeWork(),
				this.flexWork.getEnableSetPerWorkHour1(), this.flexWork.getEnableSetPerWorkHour2(),
				this.flexWork.getAdditionWithinMonthlyStatutory());
	}

	private WorkDepLabor toDomainIrregularWork(String companyId) {
		if (this.irregularWork == null) {
			return null;
		}
		return WorkDepLabor.createFromJavaType(companyId, this.irregularWork.getCalcActualOperationPre(),
				this.irregularWork.getExemptTaxTimePre(), this.irregularWork.getIncChildNursingCarePre(), this.irregularWork.getAdditionTimePre(),
				this.irregularWork.getNotDeductLateleavePre(),this.irregularWork.getDeformatExcValue(), this.irregularWork.getExemptTaxTimeWork(),
				this.irregularWork.getMinusAbsenceTimeWork(), this.irregularWork.getCalcActualOperationWork(),
				this.irregularWork.getIncChildNursingCareWork(), this.irregularWork.getNotDeductLateleaveWork(),this.irregularWork.getAdditionTimeWork(),
				this.irregularWork.getEnableSetPerWorkHour1(), this.irregularWork.getEnableSetPerWorkHour2());
	}
	
	private HourlyPaymentAdditionSet todomainHourlyPaymentAdd(String companyId) {
		if (this.hourlyPaymentAddCommand == null) {
			return null;
		}
		return HourlyPaymentAdditionSet.createFromJavaType(companyId, this.hourlyPaymentAddCommand.getCalcPremiumVacation(), this.hourlyPaymentAddCommand.getAddition1(), 
				this.hourlyPaymentAddCommand.getDeformatExcValue(), this.hourlyPaymentAddCommand.getIncChildNursingCare(), this.hourlyPaymentAddCommand.getDeduct(), 
				this.hourlyPaymentAddCommand.getCalculateIncludeIntervalExemptionTime1(), 
				this.hourlyPaymentAddCommand.getCalcWorkHourVacation() , this.hourlyPaymentAddCommand.getAddition2(), this.hourlyPaymentAddCommand.getCalculateIncludCareTime(), 
				this.hourlyPaymentAddCommand.getNotDeductLateLeaveEarly(), 
				this.hourlyPaymentAddCommand.getCalculateIncludeIntervalExemptionTime2(), this.hourlyPaymentAddCommand.getEnableSetPerWorkHour1(), this.hourlyPaymentAddCommand.getEnableSetPerWorkHour2());
	}
}
