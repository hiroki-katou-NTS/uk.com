package nts.uk.ctx.pr.core.dom.wageprovision.processdatecls;

public enum PreviousMonthClassification {
	THIS_MONTH(0,"Enum_PreviousMonthClassification_THIS_MONTH"),
	LAST_MONTH(1,"Enum_PreviousMonthClassification_LAST_MONTH");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private PreviousMonthClassification(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
