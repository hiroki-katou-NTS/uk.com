package nts.uk.ctx.at.function.dom.annualworkschedule.enums;

/*
 * 値の出力形式
 * */
public enum ValueOuputFormat {
	/*
	 * 回数
	 * */
	TIMES(0 , "回数"),
	
	/*
	 * 日数
	 * */
	DAYS(1, "日数"),
	
	/*
	 * 時間
	 * */
	TIME(2, "時間"),
	
	/*
	 * 金額
	 * */
	AMOUNT(3, "金額");
	
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;
	
	private ValueOuputFormat(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
