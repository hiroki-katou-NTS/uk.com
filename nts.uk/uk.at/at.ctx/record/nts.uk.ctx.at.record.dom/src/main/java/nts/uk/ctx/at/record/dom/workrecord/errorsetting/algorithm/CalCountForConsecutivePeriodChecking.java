package nts.uk.ctx.at.record.dom.workrecord.errorsetting.algorithm;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.errorsetting.ContinuousCount;

/**
 * 連続期間のカウントを計算
 *
 */
@Stateless
public class CalCountForConsecutivePeriodChecking {
	/**
	 * 連続期間のカウントを計算
	 * 
	 * @param count カウント
	 * @param continuousPeriod 連続期間
	 * @param errorAtr エラー発生区分
	 * @param ymd 年月日
	 * 
	 */
	public void getContinuousCount(Optional<ContinuousCount> optContinuousCount, int count, int continuousPeriod, boolean errorAtr, GeneralDate ymd) {
		// エラー発生区分をチェック
		if (errorAtr) {
			count = count + 1;
		}
		
		// カウントと連続期間を比べる
		// カウント　＞＝　連続期間
		boolean isCount = count >= continuousPeriod;
		if (isCount) {
			// 連続年月日　＝　カウント
			optContinuousCount = Optional.of(new ContinuousCount(count));
		}
		
		count = 0;
	}
}
