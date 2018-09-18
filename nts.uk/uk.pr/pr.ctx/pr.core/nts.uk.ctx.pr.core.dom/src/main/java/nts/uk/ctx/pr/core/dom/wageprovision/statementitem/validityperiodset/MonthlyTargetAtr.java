package nts.uk.ctx.pr.core.dom.wageprovision.statementitem.validityperiodset;

/**
 * 月ごとの対象区分
 */
public enum MonthlyTargetAtr {

	NOT_COVERED(0, "対象外"), 
	COVERED(1, "対象");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private MonthlyTargetAtr(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
