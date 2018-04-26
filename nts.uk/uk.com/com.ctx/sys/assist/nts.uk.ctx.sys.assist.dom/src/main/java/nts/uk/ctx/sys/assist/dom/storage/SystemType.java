package nts.uk.ctx.sys.assist.dom.storage;

/**
 * 
 * @author TrungBV システム種類
 *
 */
public enum SystemType {
	PERSON_SYSTEM(0, "Enum_SystemType_PERSON_SYSTEM"),
	ATTENDANCE_SYSTEM(1, "Enum_SystemType_ATTENDANCE_SYSTEM"),
	PAYROLL_SYSTEM(2, "Enum_SystemType_PAYROLL_SYSTEM"),
	OFFICE_HELPER(3, "Enum_SystemType_OFFICE_HELPER");
	
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private SystemType(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
