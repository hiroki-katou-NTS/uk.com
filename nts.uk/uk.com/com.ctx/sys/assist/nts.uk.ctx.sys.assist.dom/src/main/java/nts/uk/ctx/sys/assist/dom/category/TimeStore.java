package nts.uk.ctx.sys.assist.dom.category;

public enum TimeStore {
	
	FULL_TIME(0,"Enum_TimeStore_FULL_TIME"),
	DAILY(1,"Enum_TimeStore_DAILY"),
	MONTHLY(2,"Enum_TimeStore_MONTHLY"),
	ANNUAL(3,"Enum_TimeStore_ANNUAL");
	
	
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private TimeStore(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
	
}