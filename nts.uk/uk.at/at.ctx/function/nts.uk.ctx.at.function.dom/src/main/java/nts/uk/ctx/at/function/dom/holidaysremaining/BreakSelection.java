package nts.uk.ctx.at.function.dom.holidaysremaining;

public enum BreakSelection {
	/**
	 * なし
	 */
	NONE(0, "Enum_BreakSelection_NONE"),
	/**
	 * 職場
	 */
	WORKPLACE(1, "Enum_BreakSelection_WORKPLACE"),
	/**
	 * 個人
	 */
	INDIVIDUAL(2, "Enum_BreakSelection_INDIVIDUAL");
	
	public final int value;
	public final String nameId;
	private BreakSelection(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}

}
