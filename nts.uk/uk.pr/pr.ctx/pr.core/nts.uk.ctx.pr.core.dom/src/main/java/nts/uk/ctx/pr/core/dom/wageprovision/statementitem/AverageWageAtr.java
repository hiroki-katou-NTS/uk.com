package nts.uk.ctx.pr.core.dom.wageprovision.statementitem;

/**
 * 
 * @author thanh.tq 平均賃金区分
 *
 */
public enum AverageWageAtr {
	// 対象外
	NOT_COVERED(0, "Enum_AverageWageAtr_NOT_COVERED"),
	// 対象
	COVERED(1, "Enum_AverageWageAtr_COVERED");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private AverageWageAtr(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
