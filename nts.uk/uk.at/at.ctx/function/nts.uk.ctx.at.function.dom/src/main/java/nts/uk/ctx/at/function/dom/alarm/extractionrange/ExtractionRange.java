package nts.uk.ctx.at.function.dom.alarm.extractionrange;

/**
 * @author thanhpv
 * 抽出する範囲
 */
public enum ExtractionRange {
	
	/**
	 * 期間
	 */
	PERIOD(0, "期間"),
	/**
	 * 単年
	 */
	YEAR(1, "単年"),
	/**
	 * 単月
	 */
	MONTH(2, "単月"),
	/**
	 * 周期
	 */
	WEEK(3, "周期"),
	
	/**
	 * 基準月指定
	 */
	DES_STANDARD_MONTH(4, "基準月指定");
	
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;
	
	private ExtractionRange(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
