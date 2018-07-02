package nts.uk.ctx.sys.assist.dom.storage;

public enum OperatingCondition {
	INPREPARATION(0, "Enum_OperatingCondition_INPREPARATION"),
	SAVING(1, "Enum_OperatingCondition_SAVING"),
	INPROGRESS(2, "Enum_OperatingCondition_INPROGRESS"),
	DELETING(3, "Enum_OperatingCondition_DELETING"),
	DONE(4, "Enum_OperatingCondition_DONE"),
	INTERRUPTION_END(5, "Enum_OperatingCondition_INTERRUPTION_END"),
	ABNORMAL_TERMINATION(6, "Enum_OperatingCondition_ABNORMAL_TERMINATION");
	
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private OperatingCondition(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
