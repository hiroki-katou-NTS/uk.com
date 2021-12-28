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
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneLateEarlySet;

/**
 * 割増時間の加算設定
 */
@NoArgsConstructor
@Getter
public class AddSettingOfPremiumTime extends DomainObject implements SerializableWithOptional{

	private static final long serialVersionUID = 1L;
	
	/** 実働のみで計算する */
	private CalcurationByActualTimeAtr calculateActualOperation = CalcurationByActualTimeAtr.CALCULATION_BY_ACTUAL_TIME;
	/** 控除時間の扱い */
	private Optional<TreatDeductTimeForCalcWorkTime> treatDeduct = Optional.empty();
	/** 休暇時間の扱い */
	private Optional<TreatVacationTimeForCalcPremium> treatVacation = Optional.empty();

	private void writeObject(ObjectOutputStream stream){
		writeObjectWithOptional(stream);
	}
	
	private void readObject(ObjectInputStream stream){
		readObjectWithOptional(stream);
	}
	
	/**
	 * Constructor
	 * @param calculateActualOperation 実働のみで計算する
	 * @param treatDeduct 控除時間の扱い
	 * @param treatVacation 休暇時間の扱い
	 */
	public AddSettingOfPremiumTime(
			CalcurationByActualTimeAtr calculateActualOperation,
			Optional<TreatDeductTimeForCalcWorkTime> treatDeduct,
			Optional<TreatVacationTimeForCalcPremium> treatVacation) {
		super();
		this.calculateActualOperation = calculateActualOperation;
		this.treatDeduct = treatDeduct;
		this.treatVacation = treatVacation;
	}

	/**
	 * Factory
	 * @param calculateActualOperation 実働のみで計算する
	 * @param treatDeduct 控除時間の扱い
	 * @param treatVacation 休暇時間の扱い
	 * @return 割増時間の加算設定
	 */
	public static AddSettingOfPremiumTime createFromJavaType(
			int calculateActualOperation,
			Optional<TreatDeductTimeForCalcWorkTime> treatDeduct,
			Optional<TreatVacationTimeForCalcPremium> treatVacation) {
		
		AddSettingOfPremiumTime myClass = new AddSettingOfPremiumTime();
		myClass.calculateActualOperation = CalcurationByActualTimeAtr.valueOf(calculateActualOperation);
		myClass.treatDeduct = treatDeduct;
		myClass.treatVacation = treatVacation;
		return myClass;
	}
	
	/**
	 * 「実働時間のみで計算する」に変更して作成する
	 * @return 「実働時間のみで計算する」に変更したインスタンス
	 */
	public AddSettingOfPremiumTime createCalculationByActualTime() {
		return new AddSettingOfPremiumTime(
				CalcurationByActualTimeAtr.CALCULATION_BY_ACTUAL_TIME,
				Optional.empty(),
				Optional.empty());
	}
	
	/**
	 * 控除時間の扱いを取得する
	 * @return 労働時間計算の控除時間の扱い
	 */
	private TreatDeductTimeForCalcWorkTime getTreatDeductPrv(){
		if (this.calculateActualOperation.isCalclationByActualTime() ||
			!this.treatDeduct.isPresent()) return TreatDeductTimeForCalcWorkTime.getCalcByActualSetting();
		return this.treatDeduct.get();
	}
	
	/**
	 * 遅刻早退を就業時間に含めるか判断する
	 * @param lateEarlySet 就業時間帯の遅刻・早退設定
	 * @return true:含める,false:含めない
	 */
	public boolean isIncludeLateEarlyInWorkTime(Optional<WorkTimezoneLateEarlySet> lateEarlySet) {
		return this.getTreatDeductPrv().getTreatLateEarlyTimeSet().isIncludeLateEarlyInWorkTime(lateEarlySet);
	}
	
	/**
	 * 育児介護時間を含めて計算するか判断する
	 * @return true：含める、false：含めない
	 */
	public boolean isCalculateIncludCareTime() {
		return this.getTreatDeductPrv().isCalculateIncludCareTime();
	}
	
	/**
	 * インターバル免除時間を含めて計算するか判断する
	 * @return true：含める、false：含めない
	 */
	public boolean isCalculateIncludIntervalExemptionTime() {
		return this.getTreatDeductPrv().isCalculateIncludIntervalExemptionTime();
	}
	
	/**
	 * 休暇分を就業時間に含めるか判断する
	 * @return true：含める、false：含めない
	 */
	public boolean isCalculateIncludVacation() {
		if (this.calculateActualOperation.isCalclationByActualTime() ||
				!this.treatVacation.isPresent()) 
			return false;
		
		return this.treatVacation.get().isCalculateIncludVacation();
	}
}
