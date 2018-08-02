package nts.uk.ctx.sys.log.dom.reference;

/*
 * author : thuongtv
 */
public enum RecordTypeEnum {
	
	LOGIN(0, "Enum_RecordType_Login"),
	START_UP(1, "Enum_RecordType_StartUp"),
	UPDATE_MASTER(2, "Enum_RecordType_UpdateMaster"),
	UPDATE_PERSION_INFO(3, "Enum_RecordType_UpdatePersionInfo"),
	DATA_REFERENCE(4, "Enum_RecordType_DataReference"),
	DATA_MANIPULATION(5, "Enum_RecordType_DataManipulation"),
	DATA_CORRECT(6, "Enum_RecordType_DataCorrect"),
	MY_NUMBER(7, "Enum_RecordType_MyNumber"),
	TERMINAL_COMMUNICATION_INFO(8, "Enum_RecordType_TerminalCommucationInfo");
	
	/** The code. */
	public final int code;

	/** The name id. */
	public final String name;

	private RecordTypeEnum(int code, String name) {
		this.code = code;
		this.name = name;
	}
	
	
	/** The Constant values. */
	private final static RecordTypeEnum[] values = RecordTypeEnum.values();
	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the role type
	 */
	public static RecordTypeEnum valueOf(int value) {
		// Find value.
		for (RecordTypeEnum val : RecordTypeEnum.values) {
			if (val.code == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
