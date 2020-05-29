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
}
