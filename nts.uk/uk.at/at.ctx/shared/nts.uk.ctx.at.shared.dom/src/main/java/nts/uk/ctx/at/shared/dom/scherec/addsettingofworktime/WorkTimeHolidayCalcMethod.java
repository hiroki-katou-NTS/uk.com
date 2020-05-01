/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime;

import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class WorkTimeHolidayCalcMethod.
 */
// 休暇の就業時間計算方法
@NoArgsConstructor
@Getter
public class WorkTimeHolidayCalcMethod extends DomainObject{
	
	/** The calculate actual operation. */
	// 実働のみで計算する
	private CalcurationByActualTimeAtr calculateActualOperation;
	
	/** The advanced set. */
	// 詳細設定
	private Optional<WorkTimeCalcMethodDetailOfHoliday> advancedSet;
	
	/**
	 * Instantiates a new work time holiday calc method.
	 *
	 * @param calculateActualOperation the calculate actual operation
	 * @param advancedSet the advanced set
	 */
	public WorkTimeHolidayCalcMethod(int calculateActualOperation,
			WorkTimeCalcMethodDetailOfHoliday advancedSet) {
		super();
		this.calculateActualOperation = CalcurationByActualTimeAtr.valueOf(calculateActualOperation);
		this.advancedSet = Optional.ofNullable(advancedSet);
	}

	public WorkTimeHolidayCalcMethod(CalcurationByActualTimeAtr calculateActualOperation,
			Optional<WorkTimeCalcMethodDetailOfHoliday> advancedSet) {
		super();
		this.calculateActualOperation = calculateActualOperation;
		this.advancedSet = advancedSet;
	}
	
	
	public WorkTimeHolidayCalcMethod changeDeduct() {
		if(this.advancedSet.isPresent()) {
			return new WorkTimeHolidayCalcMethod(this.calculateActualOperation, this.advancedSet.map(c -> new WorkTimeCalcMethodDetailOfHoliday(c.getIncludeVacationSet(), c.getCalculateIncludCareTime(),
					c.getNotDeductLateLeaveEarly().changeDeduct(), c.getCalculateIncludIntervalExemptionTime(), c.getMinusAbsenceTime())));
		}else {
			return new WorkTimeHolidayCalcMethod(this.calculateActualOperation,this.advancedSet);
		}
	}

	public WorkTimeHolidayCalcMethod(int calculateActualOperation, Optional<WorkTimeCalcMethodDetailOfHoliday> emptyItem) {
		super();
		this.calculateActualOperation = CalcurationByActualTimeAtr.valueOf(calculateActualOperation);
		this.advancedSet = emptyItem;
	}
	
	/**
	 * 「実働時間のみで計算する」に変更して作成する
	 * @return 「実働時間のみで計算する」に変更したインスタンス
	 */
	public WorkTimeHolidayCalcMethod createCalculationByActualTime() {
		return new WorkTimeHolidayCalcMethod(CalcurationByActualTimeAtr.CALCULATION_BY_ACTUAL_TIME, this.advancedSet);
	}
}

