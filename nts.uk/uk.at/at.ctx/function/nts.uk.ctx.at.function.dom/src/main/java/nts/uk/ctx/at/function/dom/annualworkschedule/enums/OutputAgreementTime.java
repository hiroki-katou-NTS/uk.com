package nts.uk.ctx.at.function.dom.annualworkschedule.enums;

/*
 * 36協定時間を出力する場合の表示形式
 * */
public enum OutputAgreementTime {
	/*
	 * 1ヶ月ごとのみ表示
	 * */
	ONCE_MONTH(0, "1ヶ月ごとのみ表示"),

	/*
	 * 1ヶ月と2ヶ月ごとを表示
	 * */
	TWO_MONTH(1, "1ヶ月と2ヶ月ごとを表示"),

	/*
	 * 1ヶ月と3ヶ月ごとを表示
	 * */
	THREE_MONTH(2, "1ヶ月と3ヶ月ごとを表示");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private OutputAgreementTime(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
