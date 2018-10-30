package nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting;

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
