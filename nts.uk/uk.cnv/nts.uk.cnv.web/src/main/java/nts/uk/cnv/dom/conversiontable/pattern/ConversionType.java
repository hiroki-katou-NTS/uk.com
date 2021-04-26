package nts.uk.cnv.dom.conversiontable.pattern;

public enum ConversionType {
	None("NONE"),
	CodeToId("CODE_TO_ID"),
	CodeToCode("CODE_TO_CODE"),
	FixedValue("FIXID_VALUE"),
	FixedValueWithCondition("FIXID_VALUE_WITH_CONDITION"),
	Parent("PARENT"),
	StringConcat("STRING_CONCAT"),
	TimeWithDayAttr("TIME_WITH_DAY_ATTR"),
	DateTimeMerge("DATETIME_MERGE"),
	Guid("GUID"),
	Password("PASSWORD"),
	FileId("FILE_ID");

	private final String id;

	private ConversionType(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}

	public static ConversionType parse(String type) {
		for (ConversionType v : values()) {
			if(v.getId().equals(type)) {
				return v;
			}
		}
		throw new IllegalArgumentException("undefined : " + type);
	}
}
