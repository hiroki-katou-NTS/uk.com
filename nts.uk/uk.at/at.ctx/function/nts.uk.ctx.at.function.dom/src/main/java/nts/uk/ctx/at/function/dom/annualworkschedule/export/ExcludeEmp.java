package nts.uk.ctx.at.function.dom.annualworkschedule.export;

/**
 * 年間勤務表(36協定チェックリスト)
 */
public enum ExcludeEmp {
	/**
	 * 印字しない
	 */
	NOT_PRINT(0),
	/**
	 * 印字する
	 */
	PRINT(1);

	public final int value;

	private ExcludeEmp(int value) {
		this.value = value;
	}
}
