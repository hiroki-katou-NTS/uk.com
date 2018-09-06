package nts.uk.ctx.pr.core.dom.wageprovision.statementitem;

/**
 * 
 * @author thanh.tq 平均賃金区分
 *
 */
public enum AverageWageAtr {

	NOT_COVERED(0, "対象外"), 
	COVERED(1, "対象");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private AverageWageAtr(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
