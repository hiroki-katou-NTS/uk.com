package nts.uk.ctx.exio.dom.exo.category;

public enum Association {
	/**
	 * 会社コード
	 */
	CID(1),
	/**
	 * 社員コード
	 */
	SID(6),
	/**
	 * 日付(年月日)
	 */
	DATE(7);

	/** The value. */
	public final int value;

	private Association(int value) {
		this.value = value;
	}
}
