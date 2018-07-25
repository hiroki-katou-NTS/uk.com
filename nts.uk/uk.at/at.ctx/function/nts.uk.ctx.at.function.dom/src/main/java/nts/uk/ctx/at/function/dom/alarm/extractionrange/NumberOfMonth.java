package nts.uk.ctx.at.function.dom.alarm.extractionrange;

public enum NumberOfMonth {
	
	/** 1ヶ月*/
	ONE_MONTH(0, "1ヶ月"),
	
	/** 2ヶ月*/
	TWO_MONTH(1, "2ヶ月"),
	
	/** 3ヶ月*/
	THREE_MONTH(2, "3ヶ月"),
	
	OTHER(3, "他に");
	
	
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;
	
	private NumberOfMonth(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
