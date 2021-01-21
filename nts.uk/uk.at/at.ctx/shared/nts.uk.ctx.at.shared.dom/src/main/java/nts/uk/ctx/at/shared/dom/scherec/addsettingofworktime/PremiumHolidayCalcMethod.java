/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.DomainObject;
import nts.gul.serialize.binary.SerializableWithOptional;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;

/**
 * The Class PremiumHolidayCalcMethod.
 */
// 休暇の割増計算方法
@NoArgsConstructor
@Getter
public class PremiumHolidayCalcMethod extends DomainObject implements SerializableWithOptional{

	/** Serializable */
	private static final long serialVersionUID = 1L;
	
	/** The calculate actual operation. */
	// 実働のみで計算する
	private CalcurationByActualTimeAtr calculateActualOperation;
	
	/** The advance set. */
	// 詳細設定
	private Optional<PremiumCalcMethodDetailOfHoliday> advanceSet;

	private void writeObject(ObjectOutputStream stream){
		writeObjectWithOptional(stream);
	}
	
	private void readObject(ObjectInputStream stream){
		readObjectWithOptional(stream);
	}
	
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
	
	/**
	 * 遅刻・早退を控除するか判断する
	 * @param commonSetting 就業時間帯の共通設定
	 * @return 控除する場合はtrueが返る
	 */
	public boolean isDeductLateLeaveEarly(Optional<WorkTimezoneCommonSet> commonSetting) {
		if(this.calculateActualOperation.isCalclationByActualTime())
			return true;
		
		if(this.advanceSet.isPresent() && this.advanceSet.get().isDeductLateLeaveEarly(commonSetting))
			return true;
		
		return false;
	}
	
	/**
	 * 育児・介護時間を含めて計算する
	 * @return true：含める、false：含めない
	 */
	public boolean isCalculateIncludCareTime() {
		if(this.calculateActualOperation.isCalclationByActualTime())
			return false;
		
		if(this.advanceSet.isPresent() && this.advanceSet.get().isCalculateIncludCareTime())
			return true;
		
		return false;
	}
}

