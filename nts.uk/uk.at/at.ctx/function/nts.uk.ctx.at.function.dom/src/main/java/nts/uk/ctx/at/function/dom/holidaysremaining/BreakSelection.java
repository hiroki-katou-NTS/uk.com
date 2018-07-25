package nts.uk.ctx.at.function.dom.holidaysremaining;

/** 改頁区分３択 **/
public enum BreakSelection {

	/** なし **/
	None(0, "Enum_BreakSelection_NONE"),

	/** 職場 **/
	Workplace(1, "Enum_BreakSelection_WORKPLACE"),

	/** 個人 **/
	Individual(2, "Enum_BreakSelection_INDIVIDUAL");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private BreakSelection(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
