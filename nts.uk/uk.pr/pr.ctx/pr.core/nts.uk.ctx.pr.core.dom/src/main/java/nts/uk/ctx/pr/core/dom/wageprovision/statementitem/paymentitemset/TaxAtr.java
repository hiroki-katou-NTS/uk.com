package nts.uk.ctx.pr.core.dom.wageprovision.statementitem.paymentitemset;

/**
 * 
 * @author thanh.tq 課税区分
 *
 */
public enum TaxAtr {
	// 課税
	TAXATION(0, "Enum_TaxAtr_TAXATION"), 
	// 非課税（限度あり）
	LIMIT_TAX_EXEMPTION(1, "Enum_TaxAtr_LIMIT_TAX_EXEMPTION"), 
	// 非課税（限度なし）
	NO_LIMIT_TAX_EXEMPTION(2,"Enum_TaxAtr_NO_LIMIT_TAX_EXEMPTION"), 
	// 通勤費（手入力）
	COMMUTING_EXPENSES_MANUAL(3, "Enum_TaxAtr_COMMUTING_EXPENSES_MANUAL"), 
	// 通勤費（定期券利用）
	COMMUTING_EXPENSES_USING_COMMUTER(4, "Enum_TaxAtr_COMMUTING_EXPENSES_USING_COMMUTER");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private TaxAtr(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
