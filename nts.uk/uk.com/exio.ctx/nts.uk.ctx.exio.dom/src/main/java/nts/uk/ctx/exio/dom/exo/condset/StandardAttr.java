package nts.uk.ctx.exio.dom.exo.condset;

public enum StandardAttr {
	/**
	 * ユーザ
	 */
	USER(0),
	/**
	 * 定型
	 */
	STANDARD(1);

	/** The value. */
	public final int value;

	private StandardAttr(int value) {
		this.value = value;
	}
}
