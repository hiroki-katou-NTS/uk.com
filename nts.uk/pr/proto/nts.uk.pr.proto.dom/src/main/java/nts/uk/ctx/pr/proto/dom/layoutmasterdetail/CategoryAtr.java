package nts.uk.ctx.pr.proto.dom.layoutmasterdetail;

/**
 * 
 * カテゴリ区分
 *
 */
public enum CategoryAtr {
	// 0:支給
	PAYMENT(0),
	// 1:控除
	DEDUCTION(1),
	// 2:勤怠
	DILIGENCE(2),
	// 3:記事
	ARTICLES(3),
	// 9:その他
	OTHER(4);

	public final int value;

	/**
	 * 
	 * 値
	 */
	public int value() {
		return value;
	}

	/**
	 * Constructor.
	 * 
	 * @param カテゴリ区分の値
	 */
	private CategoryAtr(int value) {
		this.value = value;
	}

	public static CategoryAtr valueOf(int value) {
		switch (value) {
		case 0:
			return PAYMENT;
		case 1:
			return DEDUCTION;
		case 2:
			return DILIGENCE;
		case 3:
			return ARTICLES;
		case 4:
			return OTHER;
		default:
			throw new RuntimeException("Invalid value of CategoryAtr");
		}
	}
}
