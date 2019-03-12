package nts.uk.ctx.at.function.dom.annualworkschedule.enums;

/*
 * 合計表示の月数
 * 36 Display format when outputting agreement time
 * */
public enum MonthsInTotalDisplay {
	/*
	 * 2ヶ月表示
	 * */
	TWO_MONTH(1, "2ヶ月ごとを出力"),

	/*
	 * 3ヶ月表示
	 * */
	THREE_MONTH(2, "3ヶ月ごとを出力");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private MonthsInTotalDisplay(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
