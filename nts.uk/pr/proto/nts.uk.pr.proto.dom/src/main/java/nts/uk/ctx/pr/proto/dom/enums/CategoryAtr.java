package nts.uk.ctx.pr.proto.dom.enums;

/** カテゴリ区分 */
public enum CategoryAtr {
	// 0:支給
	PAYMENT(0),
	// 1:控除
	DEDUCTION(1),
	// 2:勤怠
	PERSONAL_TIME(2),
	// 3:記事
	ARTICLES(3),
	// 9:その他
	OTHER(9),

	/**
	 * 印字しない
	 */
	DO_NOT_PRINT(-1);

	public final int value;

	private CategoryAtr(int value) {
		this.value = value;
	}

}
