package nts.uk.ctx.exio.dom.exo.condset;

public enum StandardAtr {
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

	private StandardAtr(int value) {
		this.value = value;
	}
}
