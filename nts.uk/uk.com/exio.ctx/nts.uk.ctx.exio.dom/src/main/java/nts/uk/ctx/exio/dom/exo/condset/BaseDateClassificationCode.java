package nts.uk.ctx.exio.dom.exo.condset;

/**
 * 基準日区分コード
 */
public enum BaseDateClassificationCode {
	
	/**
	 * 締め開始日
	 */
	DEADLINE_START(1),
	
	/**
	 * 締め終了日
	 */
	DEADLINE_END(2),
	
	/**
	 * システム日付
	 */
	SYSTEM_DATE(3),
	
	/**
	 * 出力期間の開始日
	 */
	OUTPUT_PERIOD_START(4),
	
	/**
	 * 出力期間の終了日
	 */
	OUTPUT_PERIOD_END(5),
	
	/**
	 * 日付指定
	 */
	DATE_SPECIFICATION(6);

	public final int value;

	private BaseDateClassificationCode(int value) {
		this.value = value;
	}
	
}
