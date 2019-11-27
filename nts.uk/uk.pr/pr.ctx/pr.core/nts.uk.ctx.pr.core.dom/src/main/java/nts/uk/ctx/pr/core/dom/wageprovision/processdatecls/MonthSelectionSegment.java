package nts.uk.ctx.pr.core.dom.wageprovision.processdatecls;

public enum MonthSelectionSegment {
	JANUARY(1,"Enum_MonthSelectionSegment_JANUARY"),
	FEBRUARY(2,"Enum_MonthSelectionSegment_FEBRUARY"),
	MARCH(3,"Enum_MonthSelectionSegment_MARCH"),
	APRIL(4,"Enum_MonthSelectionSegment_APRIL"),
	MAY(5,"Enum_MonthSelectionSegment_SECOND_MAY"),
	JUNE(6,"Enum_MonthSelectionSegment_JUNE"),
	JULY(7,"Enum_MonthSelectionSegment_JULY"),
	AUGUST(8,"Enum_MonthSelectionSegment_AUGUST"),
	SEPTEMBER(9,"Enum_MonthSelectionSegment_SEPTEMBER"),
	OCTOBER(10,"Enum_MonthSelectionSegment_SECOND_OCTOBER"),
	NOVEMBER(11,"Enum_MonthSelectionSegment_NOVEMBER"),
	DECEMBER(12,"Enum_MonthSelectionSegment_DECEMBER");


	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private MonthSelectionSegment(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
