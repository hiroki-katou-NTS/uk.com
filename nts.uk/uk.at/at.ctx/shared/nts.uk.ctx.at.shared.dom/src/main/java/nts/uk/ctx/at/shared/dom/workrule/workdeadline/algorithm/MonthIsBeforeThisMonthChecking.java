package nts.uk.ctx.at.shared.dom.workrule.workdeadline.algorithm;

import javax.ejb.Stateless;

import nts.arc.time.YearMonth;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.shared.就業規則.就業締め日.アルゴリズム.Query.処理年月と締め期間を取得する.クラス.当月より前の月かチェック
 */
@Stateless
public class MonthIsBeforeThisMonthChecking {

	/**
	 * 当月より前の月かチェック
	 * @param ym 年月
	 * @param processingYm 現在の締め期間．処理年月
	 * @return true if 年月　＜　現在の締め期間．処理年月 else false
	 */
	public boolean checkMonthIsBeforeThisMonth(YearMonth ym, YearMonth processingYm) {
		// Input．年月　＜　Input．現在の締め期間．処理年月
		if (ym.lessThan(processingYm)) {
			// 当月より前の月か　＝　True
			return true;
		}
		
		// 以外の場合
		// 当月より前の月か　＝　False
		return false;
	}
	
}
