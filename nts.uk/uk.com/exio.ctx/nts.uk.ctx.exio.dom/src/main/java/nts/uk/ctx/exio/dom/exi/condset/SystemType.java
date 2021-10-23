package nts.uk.ctx.exio.dom.exi.condset;

/**
 * 
 * @author DatLH システム種類
 *
 */
public enum SystemType {
	/**
	 * 人事システム
	 */
	PERSON_SYSTEM(0, "Enum_SystemType_PERSON_SYSTEM"),
	/**就業システム	 */
	ATTENDANCE_SYSTEM(1, "Enum_SystemType_ATTENDANCE_SYSTEM"),
	/**
	 * 給与システム
	 */
	PAYROLL_SYSTEM(2, "Enum_SystemType_PAYROLL_SYSTEM"),
	/**
	 * オフィスヘルパー
	 */
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
