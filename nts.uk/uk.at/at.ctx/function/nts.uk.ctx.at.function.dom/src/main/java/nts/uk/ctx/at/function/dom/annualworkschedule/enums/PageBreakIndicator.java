package nts.uk.ctx.at.function.dom.annualworkschedule.enums;

/**
 * 改頁区分
 */
public enum PageBreakIndicator {
	/**
	 * なし
	 */
	NONE(0, "KWR008_56"),

	/**
	 * 職場
	 */
	WORK_PLACE(1, "KWR008_55");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private PageBreakIndicator(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
