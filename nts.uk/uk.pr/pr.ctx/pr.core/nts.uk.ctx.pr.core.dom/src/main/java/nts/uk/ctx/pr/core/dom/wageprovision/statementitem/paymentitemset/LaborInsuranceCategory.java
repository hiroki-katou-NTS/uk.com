package nts.uk.ctx.pr.core.dom.wageprovision.statementitem.paymentitemset;

/**
 * 
 * @author thanh.tq 労働保険区分
 *
 */
public enum LaborInsuranceCategory {
	// 対象外
	NOT_COVERED(0, "Enum_LaborInsuranceCategory_NOT_COVERED"),
	// 対象
	COVERED(1, "Enum_LaborInsuranceCategory_COVERED");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private LaborInsuranceCategory(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
