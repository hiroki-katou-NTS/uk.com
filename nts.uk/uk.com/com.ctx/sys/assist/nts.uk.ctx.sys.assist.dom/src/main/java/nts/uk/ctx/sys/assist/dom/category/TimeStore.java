package nts.uk.ctx.sys.assist.dom.category;

public enum TimeStore {
	
	MONTHLY(0,"Enum_TimeStore_MONTHLY"),
	ANNUAL(1,"Enum_TimeStore_ANNUAL"),
	FULL_TIME(2,"Enum_TimeStore_FULL_TIME"),
	DAILY(3,"Enum_TimeStore_DAILY");
	
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private TimeStore(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
	
}
