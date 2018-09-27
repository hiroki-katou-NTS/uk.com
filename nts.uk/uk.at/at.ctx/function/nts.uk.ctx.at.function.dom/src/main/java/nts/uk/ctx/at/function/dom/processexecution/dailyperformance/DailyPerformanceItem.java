package nts.uk.ctx.at.function.dom.processexecution.dailyperformance;


/**
 * 作成・計算項目
 */
public enum DailyPerformanceItem {
	/* 01 作成：前回処理日 計算：前回処理日～当日 */
	FIRST_OPT(0),
	
	/* 02 作成：前回処理日 計算：当月開始日～当日 */
	SECOND_OPT(1),
	
	/* 03 作成：当月開始日 計算：当月開始日～当日 */
	THIRD_OPT(2),	
	
	/* 04 作成：当月開始日 計算：当月開始日～当月終了日 */
	FOURTH_OPT(3),
	
	/* 05 作成：当月開始日 計算：当月開始日～翌月終了日 */
	FIFTH_OPT(4),
	
	/* 06 作成：翌月開始日 計算：翌月開始日～翌月終了日 */
	SIXTH_OPT(5),
	
	/* 07 作成：翌々月同一日 計算：その翌日（月末のみ末日まで） */
	SEVENTH_OPT(6);
	
	
	
	/** The value. */
	public final int value;
	private DailyPerformanceItem(int type) {
		this.value = type;
	}
	
}
