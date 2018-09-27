package nts.uk.ctx.at.function.dom.annualworkschedule.export;

/**
 * 印刷形式
 */
public enum PrintFormat {
	/**
	 * 勤怠チェックリスト
	 */
	ATTENDANCE(0),
	/**
	 * 36協定チェックリスト
	 */
	AGREEMENT_36(1);

	public final int value;

	private PrintFormat(int value) {
		this.value = value;
	}
}
