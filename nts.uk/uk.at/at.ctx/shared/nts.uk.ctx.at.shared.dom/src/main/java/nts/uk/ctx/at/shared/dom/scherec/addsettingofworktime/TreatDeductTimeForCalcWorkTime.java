package nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 労働時間計算の控除時間の扱い
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TreatDeductTimeForCalcWorkTime {

	/** 育児介護時間を含めて計算する */
	private NotUseAtr calculateIncludCareTime = NotUseAtr.NOT_USE;
	/** 遅刻早退時間の扱い設定 */
	private TreatLateEarlyTimeSetUnit treatLateEarlyTimeSet = new TreatLateEarlyTimeSetUnit();
	/** インターバル免除時間を含めて計算する */
	private NotUseAtr calculateIncludIntervalExemptionTime = NotUseAtr.NOT_USE;

	/**
	 * ファクトリー
	 * @param calculateIncludCareTime 育児介護時間を含めて計算する
	 * @param treatLateEarlyTimeSet 遅刻早退時間の扱い設定
	 * @param calculateIncludIntervalExemptionTime インターバル免除時間を含めて計算する
	 * @return 休暇の計算方法共通設定
	 */
	public static TreatDeductTimeForCalcWorkTime createFromJavaType(
			int calculateIncludCareTime,
			TreatLateEarlyTimeSetUnit treatLateEarlyTimeSet,
			int calculateIncludIntervalExemptionTime){
		
		TreatDeductTimeForCalcWorkTime myClass = new TreatDeductTimeForCalcWorkTime();
		myClass.calculateIncludCareTime = NotUseAtr.valueOf(calculateIncludCareTime);
		myClass.treatLateEarlyTimeSet = treatLateEarlyTimeSet;
		myClass.calculateIncludIntervalExemptionTime = NotUseAtr.valueOf(calculateIncludIntervalExemptionTime);
		return myClass;
	}
	
	/**
	 * 遅刻早退を就業時間に含めない設定に変更する
	 * @return 労働時間計算の控除時間の扱い
	 */
	public TreatDeductTimeForCalcWorkTime changeNotInclude() {
		return new TreatDeductTimeForCalcWorkTime(
				this.calculateIncludCareTime,
				TreatLateEarlyTimeSetUnit.createNotInclude(),
				this.calculateIncludIntervalExemptionTime);
	}
	
	/**
	 * 実働のみで計算する時の設定を取得する
	 * @return 労働時間計算の控除時間の扱い
	 */
	public static TreatDeductTimeForCalcWorkTime getCalcByActualSetting(){
		return new TreatDeductTimeForCalcWorkTime(
				NotUseAtr.NOT_USE,
				TreatLateEarlyTimeSetUnit.createNotInclude(),
				NotUseAtr.NOT_USE);
	}
	
	/**
	 * 育児介護時間を含めて計算する
	 * @return true：含める、false：含めない
	 */
	public boolean isCalculateIncludCareTime() {
		return this.calculateIncludCareTime.isUse();
	}
	
	/**
	 * インターバル免除時間を含めて計算する
	 * @return true：含める、false：含めない
	 */
	public boolean isCalculateIncludIntervalExemptionTime() {
		return this.calculateIncludCareTime.isUse();
	}
}
