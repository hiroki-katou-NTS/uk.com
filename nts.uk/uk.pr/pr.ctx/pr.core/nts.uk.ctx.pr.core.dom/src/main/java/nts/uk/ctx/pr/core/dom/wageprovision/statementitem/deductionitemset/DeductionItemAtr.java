package nts.uk.ctx.pr.core.dom.wageprovision.statementitem.deductionitemset;

/**
 * 
 * @author thanh.tq 控除項目区分
 * 
 */
public enum DeductionItemAtr {

	OPTIONAL_DEDUCTION_ITEM(0, "任意控除項目"), 
	SOCIAL_INSURANCE_ITEM(1, "社会保険項目"), 
	INCOME_TAX_ITEM(2,"所得税項目"), 
	INHABITANT_TAX_ITEM(3, "住民税項目");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private DeductionItemAtr(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
