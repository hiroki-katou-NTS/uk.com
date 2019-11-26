package nts.uk.ctx.pr.core.dom.laborinsurance;

/**
 * 登録モード
 */
public enum EditMethod {
	/**
	 * 新規
	 */
	DELETE(0),
	/**
	 * 更新
	 */
	UPDATE(1);
	/** The value. */
	public final int value;

	private EditMethod(int value) {
		this.value = value;
	}
}
