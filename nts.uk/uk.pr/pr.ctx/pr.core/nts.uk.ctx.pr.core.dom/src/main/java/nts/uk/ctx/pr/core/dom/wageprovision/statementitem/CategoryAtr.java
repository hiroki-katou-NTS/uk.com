package nts.uk.ctx.pr.core.dom.wageprovision.statementitem;

/**
 * 
 * @author thanh.tq カテゴリ区分
 * 
 */
public enum CategoryAtr {
	// 支給項目
	PAYMENT_ITEM(0, "Enum_CategoryAtr_PAYMENT_ITEM"),
	// 控除項目
	DEDUCTION_ITEM(1, "Enum_CategoryAtr_PAYMENT_DEDUCTION_ITEM"),
	// 勤怠項目
	ATTEND_ITEM(2, "Enum_CategoryAtr_PAYMENT_ATTEND_ITEM"),
	// 記事項目
	REPORT_ITEM(3, "Enum_CategoryAtr_PAYMENT_REPORT_ITEM"), 
	// その他項目
	OTHER_ITEM(4, "Enum_CategoryAtr_PAYMENT_OTHER_ITEM"),;

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private CategoryAtr(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
