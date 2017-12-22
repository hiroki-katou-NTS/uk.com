package nts.uk.ctx.at.function.dom.alarm.extractionrange;

/**
 * @author thanhpv
 * 抽出する範囲
 */
public enum ExtractionRange {
	/**
	 * 単年
	 */
	YEAR(0, "単年"),
	/**
	 * 単月
	 */
	MONTH(1, "単月"),
	/**
	 * 周期
	 */
	WEEK(2, "周期"),
	/**
	 * 期間
	 */
	PERIOD(3, "期間");
	
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;
	
	private ExtractionRange(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
