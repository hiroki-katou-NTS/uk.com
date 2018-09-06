package nts.uk.ctx.pr.core.dom.wageprovision.statementitem;

/**
 * 
 * @author thanh.tq カテゴリ区分
 * 
 */
public enum CategoryAtr {

	PAYMENT_ITEM(0, "支給項目"),
	DEDUCTION_ITEM(1, "控除項目"),
	ATTEND_ITEM(2, "勤怠項目"),
	REPORT_ITEM(3, "記事項目"), 
	OTHER_ITEM(4, "その他項目"),;

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private CategoryAtr(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
