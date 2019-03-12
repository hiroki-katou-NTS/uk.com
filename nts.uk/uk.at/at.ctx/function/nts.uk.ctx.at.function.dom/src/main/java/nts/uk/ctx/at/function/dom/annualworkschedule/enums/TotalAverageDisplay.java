package nts.uk.ctx.at.function.dom.annualworkschedule.enums;

/*
 * 合計平均表示
 * */
public enum TotalAverageDisplay {
	/*
	 * 合計
	 * */
	TOTAL(1, "合計"),

	/*
	 * 平均
	 * */
	AVERAGE(2, "平均");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private TotalAverageDisplay(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
