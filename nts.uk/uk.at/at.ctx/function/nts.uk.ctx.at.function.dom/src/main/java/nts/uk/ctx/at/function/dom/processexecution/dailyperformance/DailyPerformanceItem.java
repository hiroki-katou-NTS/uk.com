package nts.uk.ctx.at.function.dom.processexecution.dailyperformance;

/**
 * The enum Daily performance item.<br>
 * Enum 作成・計算項目
 *
 * @author nws-minhnb
 */
public enum DailyPerformanceItem {

	/**
	 * 01 作成：前回処理日 計算：前回処理日～当日
	 */
	FIRST_OPT(0, "前回処理日 計算：前回処理日～当日"),

	/**
	 * 02 作成：前回処理日 計算：当月開始日～当日
	 */
	SECOND_OPT(1, "前回処理日 計算：当月開始日～当日"),

	/**
	 * 03 作成：当月開始日 計算：当月開始日～当日
	 */
	THIRD_OPT(2, "当月開始日 計算：当月開始日～当日"),

	/**
	 * 04 作成：当月開始日 計算：当月開始日～当月終了日
	 */
	FOURTH_OPT(3, "当月開始日 計算：当月開始日～当月終了日"),

	/**
	 * 05 作成：当月開始日 計算：当月開始日～翌月終了日
	 */
	FIFTH_OPT(4, "当月開始日 計算：当月開始日～翌月終了日"),

	/**
	 * 06 作成：翌月開始日 計算：翌月開始日～翌月終了日
	 */
	SIXTH_OPT(5, "翌月開始日 計算：翌月開始日～翌月終了日"),

	/**
	 * 07 作成：翌々月同一日 計算：その翌日（月末のみ末日まで）
	 */
	SEVENTH_OPT(6, "翌々月同一日 計算：その翌日（月末のみ末日まで）");

	public final int value;
	public final String nameId;

	private DailyPerformanceItem(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}

}
