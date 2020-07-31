/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.PremiumAtr;
import nts.uk.shr.com.enumcommon.NotUseAtr;


/**
 * The Class HourlyPaymentAdditionSet.
 */
@Getter
@NoArgsConstructor
/*時給者の加算設定*/
public class HourlyPaymentAdditionSet extends AggregateRoot implements Serializable, AddSetting{

	/** Serializable */
	private static final long serialVersionUID = 1L;
	
	/**  会社ID. */
	private String companyId;

	// 休暇の計算方法の設定
	private HolidayCalcMethodSet vacationCalcMethodSet;
	
	public static HourlyPaymentAdditionSet createFromJavaType(String companyId, int calcPremiumVacation,
			int addition1, int deformatExcValue, int incChildNursingCare, int deduct, int calculateIncludeIntervalExemptionTime1,
			int calcWorkHourVacation, int addition2, int calculateIncludCareTime, int notDeductLateLeaveEarly,
			int calculateIncludeIntervalExemptionTime2, int enableSetPerWorkHour1) {
		return new HourlyPaymentAdditionSet(companyId, calcPremiumVacation,
				addition1, deformatExcValue,
				incChildNursingCare, deduct, 
				calculateIncludeIntervalExemptionTime1, 
				calcWorkHourVacation,
				addition2,
				calculateIncludCareTime,
				notDeductLateLeaveEarly,
				calculateIncludeIntervalExemptionTime2,
				enableSetPerWorkHour1);
	}
	
	/**
	 * constructor
	 * @param companyId
	 * @param vacationCalcMethodSet
	 */
	public HourlyPaymentAdditionSet(String companyId, HolidayCalcMethodSet vacationCalcMethodSet) {
		super();
		this.companyId = companyId;
		this.vacationCalcMethodSet = vacationCalcMethodSet;
	}

	/**
	 * Instantiates a new hourly payment addition set.
	 *
	 * @param companyId the company id
	 * @param calcPremiumVacation the calc premium vacation
	 * @param addition1 the addition 1
	 * @param deformatExcValue the deformat exc value
	 * @param incChildNursingCare the inc child nursing care
	 * @param deduct the deduct
	 * @param calculateIncludeIntervalExemptionTime1 the calculate include interval exemption time 1
	 * @param calcWorkHourVacation the calc work hour vacation
	 * @param addition2 the addition 2
	 * @param calculateIncludCareTime the calculate includ care time
	 * @param notDeductLateLeaveEarly the not deduct late leave early
	 * @param calculateIncludeIntervalExemptionTime2 the calculate include interval exemption time 2
	 * @param enableSetPerWorkHour1 the enable set per work hour 1
	 * @param enableSetPerWorkHour2 the enable set per work hour 2
	 */
	public HourlyPaymentAdditionSet(String companyId, int calcPremiumVacation, 
			int addition1, int deformatExcValue, 
			int incChildNursingCare, int deduct,  
			int calculateIncludeIntervalExemptionTime1, int calcWorkHourVacation,
			int addition2, int calculateIncludCareTime, int notDeductLateLeaveEarly,
			int calculateIncludeIntervalExemptionTime2, int enableSetPerWorkHour1) {
		super();
		this.companyId = companyId;
		IncludeHolidaysPremiumCalcDetailSet includeHolidaysPremiumCalcDetailSet = new IncludeHolidaysPremiumCalcDetailSet(addition1, deformatExcValue, null);
		DeductLeaveEarly deductLeaveEarly = new DeductLeaveEarly(deduct, enableSetPerWorkHour1);
		PremiumCalcMethodDetailOfHoliday advanceSetPre = new PremiumCalcMethodDetailOfHoliday(includeHolidaysPremiumCalcDetailSet, incChildNursingCare, 
																							deductLeaveEarly, calculateIncludeIntervalExemptionTime1);
		PremiumHolidayCalcMethod premiumHolidayCalcMethod = new PremiumHolidayCalcMethod(calcPremiumVacation, advanceSetPre);
		
		EmploymentCalcDetailedSetIncludeVacationAmount includeVacationSet 
																	= new EmploymentCalcDetailedSetIncludeVacationAmount(addition2, null, 
																														null, 
																														null);
		DeductLeaveEarly deductLeaveEarly2 = new DeductLeaveEarly(notDeductLateLeaveEarly, enableSetPerWorkHour1);
		WorkTimeCalcMethodDetailOfHoliday advanceSetWork = new WorkTimeCalcMethodDetailOfHoliday(includeVacationSet, calculateIncludCareTime, 
																									deductLeaveEarly2, 
																									calculateIncludeIntervalExemptionTime2, null);
		WorkTimeHolidayCalcMethod workTimeHolidayCalcMethod = new WorkTimeHolidayCalcMethod(calcWorkHourVacation, advanceSetWork);
		
		HolidayCalcMethodSet calcMethodSet = new HolidayCalcMethodSet(premiumHolidayCalcMethod, workTimeHolidayCalcMethod);
		
		this.vacationCalcMethodSet = calcMethodSet;
	}
	
	/**
	 * 休暇加算するかを取得する
	 * @param premiumAtr
	 * @return 加算する：USE 加算しない：NOT_USE
	 */
	public NotUseAtr getNotUseAtr(PremiumAtr premiumAtr) {
		return this.vacationCalcMethodSet.getNotUseAtr(premiumAtr);
	}
	
	/**
	 * 実働のみで計算するかを取得する
	 * @param premiumAtr
	 * @return 実働時間のみで計算する：CALCULATION_BY_ACTUAL_TIME 実働時間以外も含めて計算する： CALCULATION_OTHER_THAN_ACTUAL_TIME
	 */
	public CalcurationByActualTimeAtr getCalculationByActualTimeAtr(PremiumAtr premiumAtr) {
		return this.vacationCalcMethodSet.getCalcurationByActualTimeAtr(premiumAtr);
	}
	
	/**
	 * 「実働時間のみで計算する」に変更して作成する
	 * @return 「実働時間のみで計算する」に変更したインスタンス
	 */
	public HourlyPaymentAdditionSet createCalculationByActualTime() {
		return new HourlyPaymentAdditionSet(this.companyId, this.vacationCalcMethodSet.createCalculationByActualTime());
	}
	
	/**
	 * 就業時間から控除するフレックス時間を求めるときの加算設定を取得する
	 * 
	 * ■この処理が必要な理由
	 * 		不足時加算（割増：実働のみ、就業時間：実働以外も含める）、
	 * 		フレックス 1ヵ月 法定177h 実働171h 年休1日(8h) の場合
	 * 
	 * 		①フレックス控除前の就業時間を計算する（179h）←年休分が加算されている
	 * 		②フレックス時間を計算する（0h）←年休分が加算されていない
	 * 		③就業時間を計算する 179h - 0h = 179h ← NG（就業時間は177h）
	 * 
	 * 		上記の場合に、②の計算で「休暇の割増計算方法」を「休暇の就業時間計算方法」で
	 * 		上書きすることによって（割増：実働以外も含める、就業時間：実働以外も含める にしたい）
	 * 		正しい計算を実現している為、この処理が必要。
	 * 		この処理を使うと下記の計算となる。
	 * 
	 * 		①フレックス控除前の就業時間を計算する（179h）←年休分が加算されている
	 * 		②フレックス時間を計算する（2h）←年休分が加算されている
	 * 		③就業時間を計算する 179h - 2h = 177h ← OK
	 * 
	 * @return 「休暇の割増計算方法」の「実働のみで計算する」を「休暇の就業時間計算方法」の「実働のみで計算する」で上書きした設定
	 */
	public HourlyPaymentAdditionSet getWorkTimeDeductFlexTime() {
		return new HourlyPaymentAdditionSet(this.companyId, this.vacationCalcMethodSet.getWorkTimeDeductFlexTime());
	}
}
