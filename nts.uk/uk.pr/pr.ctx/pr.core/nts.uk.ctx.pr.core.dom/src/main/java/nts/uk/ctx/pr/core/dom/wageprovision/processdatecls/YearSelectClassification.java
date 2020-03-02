package nts.uk.ctx.pr.core.dom.wageprovision.processdatecls;

public enum YearSelectClassification {
	PREVIOUS_YEAR(0,"Enum_YearSelectClassification_PREVIOUS_YEAR"),
	THIS_YEAR(1,"Enum_YearSelectClassification_THIS_YEAR"),
	AFTER_YEAR(2,"Enum_YearSelectClassification_AFTER_YEAR"),
	LEAP_YEAR(3,"Enum_YearSelectClassification_LEAP_YEAR");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private YearSelectClassification(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
