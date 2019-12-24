package nts.uk.ctx.pr.core.dom.wageprovision.statementitem.paymentitemset;

/**
 * 
 * @author thanh.tq 限度金額指定区分
 *
 */
public enum LimitAmountClassification {

	TAX_EXEMPTION_LIMIT_MASTER(0, "非課税限度額マスタを参照"), 
	FIXED_AMOUNT(1, "固定額"), 
	REFER_TO_PERSONAL_TRANSPORTATION_TOOL_LIMIT(2, "個人の交通用具限度額を参照"), 
	REFER_TO_PERSONAL_TRANSPORTATION_LIMIT(3, "個人の交通機関限度額を参照");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private LimitAmountClassification(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
