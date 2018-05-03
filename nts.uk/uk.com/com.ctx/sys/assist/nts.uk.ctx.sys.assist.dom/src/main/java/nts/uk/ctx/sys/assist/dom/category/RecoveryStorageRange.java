package nts.uk.ctx.sys.assist.dom.category;

public enum RecoveryStorageRange {
	
	EMPLOYEE_UNIT(0,"Enum_Recovery_Storage_Range_EMPLOYEE_UNIT"),
	COMPANY_UNIT(1,"Enum_Recovery_Storage_Range_COMPANY_UNIT");
	
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private RecoveryStorageRange(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
	
}
