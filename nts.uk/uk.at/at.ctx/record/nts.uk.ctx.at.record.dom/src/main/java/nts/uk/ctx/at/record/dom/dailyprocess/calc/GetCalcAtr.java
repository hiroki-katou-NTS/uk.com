package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import nts.uk.ctx.at.record.dom.calculationattribute.BonusPayAutoCalcSet;
import nts.uk.ctx.at.record.dom.calculationattribute.CalAttrOfDailyPerformance;

/**
 * 計算区分取得用のロジック
 * @author keisuke_hoshina
 */
public class GetCalcAtr {

	/**
	 * 加給時間の計算をするか判定する
	 * @param calcAtr 加給の自動計算区分
	 * @param calcSet 
	 * @param bonusPayAutoCalcSet 加給自動計算設定
	 * @param actualAtr 実働時間帯区分
	 * @return
	 */
	public static boolean isCalc(boolean calcAtr,CalAttrOfDailyPerformance calcSet, BonusPayAutoCalcSet bonusPayAutoCalcSet, ActualWorkTimeSheetAtr actualAtr) {
		if(calcAtr) {
			return bonusPayAutoCalcSet.getCalcAtr(actualAtr, calcSet);
		}
		else {
			return false;
		}
	}
}
