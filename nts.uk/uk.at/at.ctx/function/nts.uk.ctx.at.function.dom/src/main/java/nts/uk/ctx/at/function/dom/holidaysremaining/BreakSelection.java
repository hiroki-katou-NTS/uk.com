package nts.uk.ctx.at.function.dom.holidaysremaining;

public enum BreakSelection {
	/**
	 * 職場
	 */
	WORKPLACE(1, "Enum_BreakSelection_WORKPLACE"),
	/**
	 * 個人
	 */
	INDIVIDUAL(2, "Enum_BreakSelection_INDIVIDUAL"),
	/**
	 * なし
	 */
	NONE(3, "Enum_BreakSelection_NONE");
	public final int value;
	public final String nameId;
	private BreakSelection(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}

}
