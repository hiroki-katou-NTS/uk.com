package nts.uk.ctx.pr.core.dom.wageprovision.statementitem.paymentitemset;

/**
 * 
 * @author thanh.tq 労働保険区分
 *
 */
public enum LaborInsuranceCategory {

	NOT_COVERED(0, "対象外"), 
	COVERED(1, "対象");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private LaborInsuranceCategory(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
