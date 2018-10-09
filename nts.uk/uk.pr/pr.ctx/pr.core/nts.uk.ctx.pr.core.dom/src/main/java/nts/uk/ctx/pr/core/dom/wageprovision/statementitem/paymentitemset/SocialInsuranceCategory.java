package nts.uk.ctx.pr.core.dom.wageprovision.statementitem.paymentitemset;

/**
 * 
 * @author thanh.tq 固定的賃金の設定区分
 *
 */
public enum SocialInsuranceCategory {

	NOT_COVERED(0, "対象外"), 
	COVERED(1, "対象");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private SocialInsuranceCategory(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
