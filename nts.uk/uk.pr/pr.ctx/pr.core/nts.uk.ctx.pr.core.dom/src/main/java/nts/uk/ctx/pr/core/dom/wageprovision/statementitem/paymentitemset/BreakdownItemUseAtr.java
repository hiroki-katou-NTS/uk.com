package nts.uk.ctx.pr.core.dom.wageprovision.statementitem.paymentitemset;

/**
 * 
 * @author thanh.tq 内訳項目利用区分
 *
 */
public enum BreakdownItemUseAtr {

	NOT_USE(0, "利用しない"), USE(1, "利用する");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private BreakdownItemUseAtr(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
