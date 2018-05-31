package nts.uk.ctx.at.function.dom.annualworkschedule.enums;

/*
 * 値の出力形式
 * */
public enum ValueOuputFormat {
	/*
	 * 時間
	 * */
	TIME(0, "Enum_ValueOutput_TIME"),
	/*
	 * 回数
	 * */
	TIMES(1 , "Enum_ValueOutput_TIMES"),
	/*
	 * 日数
	 * */
	DAYS(2, "Enum_ValueOutput_DAYS"),
	/*
	 * 金額
	 * */
	AMOUNT(3, "Enum_ValueOutput_AMOUNT_OF_MONEY");
	
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;
	
	private ValueOuputFormat(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
