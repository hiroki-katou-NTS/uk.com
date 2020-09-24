/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime;

import java.io.Serializable;
import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.PremiumAtr;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * The Class HolidayCalcMethodSet.
 */

@NoArgsConstructor
@Getter
// 休暇の計算方法の設定
public class HolidayCalcMethodSet extends DomainObject implements Serializable{

	/** Serializable */
	private static final long serialVersionUID = 1L;
	
	/** The premium calc method detail of holiday. */
	// 休暇の割増計算方法
	private PremiumHolidayCalcMethod premiumCalcMethodOfHoliday;
	
	/** The work time calc method detail of holiday. */
	// 休暇の就業時間計算方法
	private WorkTimeHolidayCalcMethod workTimeCalcMethodOfHoliday;

	/**
	 * @param premiumCalcMethodOfHoliday
	 * @param workTimeCalcMethodOfHoliday
	 */
	public HolidayCalcMethodSet(PremiumHolidayCalcMethod premiumCalcMethodOfHoliday,
			WorkTimeHolidayCalcMethod workTimeCalcMethodOfHoliday) {
		super();
		this.premiumCalcMethodOfHoliday = premiumCalcMethodOfHoliday;
		this.workTimeCalcMethodOfHoliday = workTimeCalcMethodOfHoliday;
	}
	
	/**
	 * aggregateが取得できなかった場合の仮作成
	 * 就業、割増共に中身が「実働のみで計算する=実働時間のみで計算する　、詳細設定=null」で作成
	 * @return
	 */
	public static HolidayCalcMethodSet emptyHolidayCalcMethodSet() {
		PremiumHolidayCalcMethod premiumHolidayCalcMethod = new PremiumHolidayCalcMethod(0,Optional.empty());
		WorkTimeHolidayCalcMethod workTimeHolidayCalcMethod = new WorkTimeHolidayCalcMethod(0,Optional.empty());
		return new HolidayCalcMethodSet(premiumHolidayCalcMethod,workTimeHolidayCalcMethod);
	}
	
	/**
	 * 休暇加算するかどうか判断
	 * @return
	 */
	public NotUseAtr getNotUseAtr(PremiumAtr premiumAtr) {
		if(premiumAtr.isRegularWork()) {
			return this.workTimeCalcMethodOfHoliday.getAdvancedSet().isPresent()?this.workTimeCalcMethodOfHoliday.getAdvancedSet().get().getIncludeVacationSet().getAddition():NotUseAtr.NOT_USE;
		}else {
			return this.premiumCalcMethodOfHoliday.getAdvanceSet().isPresent()?this.workTimeCalcMethodOfHoliday.getAdvancedSet().get().getIncludeVacationSet().getAddition():NotUseAtr.NOT_USE;
		}
	}
	
	/**
	 * 割増区分を基に実働のみで計算するしない区分を取得する
	 * @param premiumAtr
	 * @return
	 */
	public CalcurationByActualTimeAtr getCalcurationByActualTimeAtr(PremiumAtr premiumAtr) {
		if(premiumAtr.isRegularWork()) {
			return this.workTimeCalcMethodOfHoliday.getCalculateActualOperation();
		}else {
			return this.premiumCalcMethodOfHoliday.getCalculateActualOperation();
		}
	}
	
	/**
	 * 「実働時間のみで計算する」に変更して作成する
	 * @return 「実働時間のみで計算する」に変更したインスタンス
	 */
	public HolidayCalcMethodSet createCalculationByActualTime() {
		return new HolidayCalcMethodSet(
				this.premiumCalcMethodOfHoliday.createCalculationByActualTime(),
				this.workTimeCalcMethodOfHoliday.createCalculationByActualTime());
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
	 * @return 「休暇の割増計算方法」を「休暇の就業時間計算方法」で上書きした設定
	 */
	public HolidayCalcMethodSet getWorkTimeDeductFlexTime() {
		return new HolidayCalcMethodSet(
				this.premiumCalcMethodOfHoliday.of(this.workTimeCalcMethodOfHoliday),
				this.workTimeCalcMethodOfHoliday);
	}
}

