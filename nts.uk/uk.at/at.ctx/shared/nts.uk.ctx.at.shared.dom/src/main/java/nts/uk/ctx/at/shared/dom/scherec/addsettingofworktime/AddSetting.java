package nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime;

import nts.uk.ctx.at.shared.dom.PremiumAtr;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 加算設定
 * @author daiki_ichioka
 *
 */
public interface AddSetting {
	
	/**
	 * 会社IDを取得する
	 * @return 会社ID
	 */
	String getCompanyId();
	
	/**
	 * 休暇加算するかを取得する
	 * @param premiumAtr
	 * @return 加算する：USE 加算しない：NOT_USE
	 */
	NotUseAtr getNotUseAtr(PremiumAtr premiumAtr);
	
	/**
	 * 実働のみで計算するかを取得する
	 * @param premiumAtr
	 * @return 実働時間のみで計算する：CALCULATION_BY_ACTUAL_TIME 実働時間以外も含めて計算する： CALCULATION_OTHER_THAN_ACTUAL_TIME
	 */
	CalcurationByActualTimeAtr getCalculationByActualTimeAtr(PremiumAtr premiumAtr);
	
	/**
	 * 休暇の計算方法の設定を取得する
	 * @return VacationCalcMethodSet
	 */
	HolidayCalcMethodSet getVacationCalcMethodSet();
	
	/**
	 * 「実働時間のみで計算する」に変更して作成する
	 * @return 「実働時間のみで計算する」に変更したインスタンス
	 */
	AddSetting createCalculationByActualTime();
	
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
	public AddSetting getWorkTimeDeductFlexTime();
}
