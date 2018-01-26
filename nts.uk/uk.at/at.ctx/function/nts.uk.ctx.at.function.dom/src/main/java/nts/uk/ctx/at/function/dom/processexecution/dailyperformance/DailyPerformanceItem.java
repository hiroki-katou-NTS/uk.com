package nts.uk.ctx.at.function.dom.processexecution.dailyperformance;

import lombok.AllArgsConstructor;

/**
 * 作成・計算項目
 */
@AllArgsConstructor
public enum DailyPerformanceItem {
	/* 01 作成：前回処理日 計算：前回処理日～当日 */
	FIRST_OPT(0, "01 作成：前回処理日 計算：前回処理日～当日"),
	
	/* 02 作成：前回処理日 計算：当月開始日～当日 */
	SECOND_OPT(1, "02 作成：前回処理日 計算：当月開始日～当日"),
	
	/* 03 作成：当月開始日 計算：当月開始日～当日 */
	THIRD_OPT(2, "03 作成：当月開始日 計算：当月開始日～当日"),
	
	/* 04 作成：当月開始日 計算：当月開始日～当月終了日 */
	FOURTH_OPT(3, "04 作成：当月開始日 計算：当月開始日～当月終了日"),
	
	/* 05 作成：当月開始日 計算：当月開始日～翌月終了日 */
	FIFTH_OPT(4, "05 作成：当月開始日 計算：当月開始日～翌月終了日"),
	
	/* 06 作成：翌月開始日 計算：翌月開始日～翌月終了日 */
	SIXTH_OPT(5, "06 作成：翌月開始日 計算：翌月開始日～翌月終了日");
	
	/** The value. */
	public final int value;
	
	public final String name;
}
