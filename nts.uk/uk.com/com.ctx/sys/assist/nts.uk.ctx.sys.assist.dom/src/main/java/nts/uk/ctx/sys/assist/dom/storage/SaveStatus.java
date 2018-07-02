package nts.uk.ctx.sys.assist.dom.storage;

public enum SaveStatus {
	SUCCESS(0, "Enum_SaveStatus_SUCCESS"),
	INTERRUPTION(1, "Enum_SaveStatus_INTERRUPTION"),
	FAILURE(2, "Enum_SaveStatus_FAILURE");
	
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private SaveStatus(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
