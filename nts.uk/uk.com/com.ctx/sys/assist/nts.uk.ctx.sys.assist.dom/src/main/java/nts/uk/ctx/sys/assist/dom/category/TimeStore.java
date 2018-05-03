package nts.uk.ctx.sys.assist.dom.category;

public enum TimeStore {
	
	MONTHLY(0,"Enum_Time_Store_MONTHLY"),
	ANNUAL(1,"Enum_Time_Store_ANNUAL"),
	FULL_TIME(2,"Enum_Time_Store_FULL_TIME"),
	DAILY(3,"Enum_Time_Store_DAILY");
	
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private TimeStore(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
	
}
