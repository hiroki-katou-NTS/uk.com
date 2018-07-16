package nts.uk.ctx.sys.assist.dom.storage;

public enum StorageForm {
	MANUAL(0, "Enum_StorageForm_MANUAL"),
	AUTOMATIC(1, "Enum_StorageForm_AUTOMATIC");
	
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private StorageForm(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
