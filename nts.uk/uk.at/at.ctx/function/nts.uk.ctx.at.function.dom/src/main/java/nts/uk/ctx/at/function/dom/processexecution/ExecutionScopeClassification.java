package nts.uk.ctx.at.function.dom.processexecution;

/**
 * The enum Execution scope classification.<br>
 * Enum 実行範囲区分
 */
public enum ExecutionScopeClassification {

	/**
	 * 会社
	 */
	COMPANY(0, "会社"),

	/**
	 * 職場
	 */
	WORKPLACE(1, "職場");

	/**
	 * The Value.
	 */
	public final int value;

	/**
	 * The Name id.
	 */
	public final String nameId;

	/**
	 * Instantiates a new <code>ExecutionScopeClassification</code>.
	 *
	 * @param value  the value
	 * @param nameId the name id
	 */
	private ExecutionScopeClassification(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}

}
