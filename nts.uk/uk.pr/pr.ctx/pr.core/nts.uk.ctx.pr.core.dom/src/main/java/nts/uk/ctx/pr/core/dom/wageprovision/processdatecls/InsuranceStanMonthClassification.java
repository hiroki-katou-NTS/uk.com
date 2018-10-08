package nts.uk.ctx.pr.core.dom.wageprovision.processdatecls;

public enum InsuranceStanMonthClassification {
	LAST_MONTH(0,"Enum_InsuranceStanMonthClassification_LAST_MONTH"),
	MONTH(1,"Enum_InsuranceStanMonthClassification_MONTH"),
	JANUARY(2,"Enum_InsuranceStanMonthClassification_JANUARY"),
	FEBRUARY(3,"Enum_InsuranceStanMonthClassification_FEBRUARY"),
	MARCH(4,"Enum_InsuranceStanMonthClassification_MARCH"),
	APRIL(5,"Enum_InsuranceStanMonthClassification_APRIL"),
	MAY(6,"Enum_InsuranceStanMonthClassification_MAY"),
	JUNE(7,"Enum_InsuranceStanMonthClassification_JUNE"),
	JULY(8,"Enum_InsuranceStanMonthClassification_JULY"),
	AUGUST(9,"Enum_InsuranceStanMonthClassification_AUGUST"),
	SEPTEMBER(10,"Enum_InsuranceStanMonthClassification_SEPTEMBER"),
	OCTOBER(11,"Enum_InsuranceStanMonthClassification_OCTOBER"),
	NOVEMBER(12,"Enum_InsuranceStanMonthClassification_NOVEMBER"),
	DECEMBER(13,"Enum_InsuranceStanMonthClassification_DECEMBER");
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private InsuranceStanMonthClassification(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
