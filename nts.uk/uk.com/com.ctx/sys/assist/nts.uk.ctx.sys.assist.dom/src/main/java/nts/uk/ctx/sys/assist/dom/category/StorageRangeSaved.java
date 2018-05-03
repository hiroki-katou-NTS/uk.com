package nts.uk.ctx.sys.assist.dom.category;

public enum StorageRangeSaved {
	
	ALL_EMP(1,"Enum_Storage_Range_Saved_ALL_EMP"),
	EARCH_EMP(0,"Enum_Storage_Range_Saved_EARCH_EMP");
	
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private StorageRangeSaved(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
