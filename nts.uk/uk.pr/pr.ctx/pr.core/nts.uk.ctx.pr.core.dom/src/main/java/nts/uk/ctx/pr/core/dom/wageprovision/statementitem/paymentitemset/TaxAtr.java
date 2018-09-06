package nts.uk.ctx.pr.core.dom.wageprovision.statementitem.paymentitemset;

/**
 * 
 * @author thanh.tq 課税区分
 *
 */
public enum TaxAtr {

	TAXATION(0, "課税"), 
	LIMIT_TAX_EXEMPTION(1, "非課税（限度あり）"), 
	NO_LIMIT_TAX_EXEMPTION(2,"非課税（限度なし）"), 
	COMMUTING_EXPENSES_MANUAL(3, "通勤費（手入力）"), 
	COMMUTING_EXPENSES_USING_COMMUTER(4, "通勤費（定期券利用）");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private TaxAtr(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
