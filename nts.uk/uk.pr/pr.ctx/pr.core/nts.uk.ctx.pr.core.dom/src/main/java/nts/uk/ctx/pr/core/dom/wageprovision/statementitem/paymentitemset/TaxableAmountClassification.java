package nts.uk.ctx.pr.core.dom.wageprovision.statementitem.paymentitemset;

/**
 * 
 * @author thanh.tq  課税金額区分
 *
 */
public enum TaxableAmountClassification {

	OVERDRAFT_TAXATION(0, "超過金額課税"), 
	FULL_TAXATION(1, "全額課税");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private TaxableAmountClassification(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
