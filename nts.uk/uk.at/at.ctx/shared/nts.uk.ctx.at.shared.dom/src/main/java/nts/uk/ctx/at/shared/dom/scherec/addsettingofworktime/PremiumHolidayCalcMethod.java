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
 * The Class PremiumHolidayCalcMethod.
 */
// 休暇の割増計算方法
@NoArgsConstructor
@Getter
public class PremiumHolidayCalcMethod extends DomainObject{
	
	/** The calculate actual operation. */
	// 実働のみで計算する
	private CalcurationByActualTimeAtr calculateActualOperation;
	
	/** The advance set. */
	// 詳細設定
	private Optional<PremiumCalcMethodDetailOfHoliday> advanceSet;

	/**
	 * @param calculateActualOperation
	 * @param advanceSet
	 */
	public PremiumHolidayCalcMethod(int calculateActualOperation,
			PremiumCalcMethodDetailOfHoliday advanceSet) {
		super();
		this.calculateActualOperation = CalcurationByActualTimeAtr.valueOf(calculateActualOperation);
		this.advanceSet = Optional.ofNullable(advanceSet);
	}

	public PremiumHolidayCalcMethod(CalcurationByActualTimeAtr calculateActualOperation,
			Optional<PremiumCalcMethodDetailOfHoliday> advanceSet) {
		super();
		this.calculateActualOperation = calculateActualOperation;
		this.advanceSet = advanceSet;
	}

	public PremiumHolidayCalcMethod(int calculateActualOperation, Optional<PremiumCalcMethodDetailOfHoliday> emptyItem) {
		super();
		this.calculateActualOperation = CalcurationByActualTimeAtr.valueOf(calculateActualOperation);
		this.advanceSet = emptyItem;
	}
	
	/**
	 * 「実働時間のみで計算する」に変更して作成する
	 * @return 「実働時間のみで計算する」に変更したインスタンス
	 */
	public PremiumHolidayCalcMethod createCalculationByActualTime() {
		return new PremiumHolidayCalcMethod(CalcurationByActualTimeAtr.CALCULATION_BY_ACTUAL_TIME, this.advanceSet);
	}
	
	/**
	 * 「休暇の就業時間計算方法」を「休暇の割増計算方法」に変換する
	 * @param workTimeHolidayCalcMethod 休暇の就業時間計算方法
	 * @return 休暇の割増計算方法
	 */
	public PremiumHolidayCalcMethod of(WorkTimeHolidayCalcMethod workTimeHolidayCalcMethod) {
		return new PremiumHolidayCalcMethod(
				workTimeHolidayCalcMethod.getCalculateActualOperation(),
				this.advanceSet.isPresent()
					?this.advanceSet.get().of(workTimeHolidayCalcMethod.getAdvancedSet())
					:Optional.empty());
	}
}

