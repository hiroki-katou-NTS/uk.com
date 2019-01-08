package nts.uk.ctx.exio.dom.exo.commonalgorithm;

/**
 * 登録モード
 */
public enum RegisterMode {
	/**
	 * 新規
	 */
	NEW(0),
	/**
	 * 更新
	 */
	UPDATE(1);
	/** The value. */
	public final int value;

	private RegisterMode(int value) {
		this.value = value;
	}
}
