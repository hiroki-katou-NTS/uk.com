package nts.uk.ctx.pr.core.dom.wageprovision.statementitem.timeitemset;

/**
 * 
 * @author thanh.tq 平均賃金区分
 *
 */
public enum TimeCountAtr {
	// 時間
	TIME(0, "Enum_TimeCountAtr_TIME"), 
	// 回数
	TIMES(1, "Enum_TimeCountAtr_TIMES");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private TimeCountAtr(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
