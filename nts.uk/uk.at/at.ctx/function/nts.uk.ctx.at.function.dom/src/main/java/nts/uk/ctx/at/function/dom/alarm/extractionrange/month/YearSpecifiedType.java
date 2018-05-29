package nts.uk.ctx.at.function.dom.alarm.extractionrange.month;

public enum YearSpecifiedType {

	/** 本年 */
	FISCAL_YEAR(1, "本年"),

	/** 本年度 */
	CURRENT_YEAR(2, "本年度");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private YearSpecifiedType(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}

}
