package nts.uk.ctx.sys.assist.dom.category;

public enum StorageRangeSaved {
	
	ALL_EMP(1,"Enum_StorageRangeSaved_ALL_EMP"),
	EARCH_EMP(0,"Enum_StorageRangeSaved_EARCH_EMP");
	
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private StorageRangeSaved(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
