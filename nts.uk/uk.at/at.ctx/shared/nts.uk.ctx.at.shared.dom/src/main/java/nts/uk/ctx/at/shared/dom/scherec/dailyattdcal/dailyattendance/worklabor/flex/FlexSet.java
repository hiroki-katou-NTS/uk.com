/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worklabor.flex;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * フレックス勤務の日別計算設定
 * @author shuichi_ishida
 */
@Getter
@AllArgsConstructor
public class FlexSet extends AggregateRoot {

	/** 会社ID */
	private String companyId;
	/** 半日休日の計算方法 */
	private FlexCalcMethodOfHalfHoliday halfHoliday;
	/** 代休取得時の計算方法 */
	private FlexCalcMethodOfCompLeave compLeave;
	/** 非勤務日計算 */
	private CalcFlexOfNoWorkingDay calcNoWorkingDay;

	/**
	 * コンストラクタ
	 */
	public FlexSet(){
		this.companyId = "Dummy";
		this.halfHoliday = new FlexCalcMethodOfHalfHoliday();
		this.compLeave = new FlexCalcMethodOfCompLeave();
		this.calcNoWorkingDay = new CalcFlexOfNoWorkingDay();
	}
	
	public static interface Require {
		Optional<FlexSet> flexSet(String companyId);
	}
	
	/**
	 * デフォルト値で作成する
	 * @return フレックス勤務の日別計算設定
	 */
	public static FlexSet defaultValue(){
		FlexSet result = new FlexSet();
		return result;
	}
	
	/**
	 * ファクトリー
	 * @param companyId 会社ID
	 * @param shortCalcHd 半日休日の計算方法.不足計算
	 * @param premiumCalcHd 半日休日の計算方法.割増計算
	 * @param isDeductFromPred 代休取得時の計算方法.所定から控除するかどうか
	 * @param premiumCalcCompLeave 代休取得時の計算方法.割増計算
	 * @param calcSetOfTimeCompLeave 代休取得時の計算方法.時間代休時の計算設定
	 * @param calcNoWorkingDay 非勤務日計算.設定
	 * @return フレックス勤務の日別計算設定
	 */
	public static FlexSet createFromJavaType(
			String companyId,
			int shortCalcHd,
			int premiumCalcHd,
			int isDeductFromPred,
			int premiumCalcCompLeave,
			int calcSetOfTimeCompLeave,
			int calcNoWorkingDay) {
		
		return new FlexSet(
				companyId,
				FlexCalcMethodOfHalfHoliday.createJavaType(
						shortCalcHd, premiumCalcHd),
				FlexCalcMethodOfCompLeave.createJavaType(
						isDeductFromPred, premiumCalcCompLeave, calcSetOfTimeCompLeave),
				CalcFlexOfNoWorkingDay.createJavaType(calcNoWorkingDay));
	}
}
