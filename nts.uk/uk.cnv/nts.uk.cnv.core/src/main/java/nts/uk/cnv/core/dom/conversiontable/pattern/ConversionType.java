package nts.uk.cnv.core.dom.conversiontable.pattern;

import nts.uk.cnv.core.dom.conversionsql.Join;
import nts.uk.cnv.core.dom.conversiontable.ConversionInfo;
import nts.uk.cnv.core.infra.entity.conversiontable.ScvmtConversionTable;

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
	FileId("FILE_ID"),
	SourceJoin("SOURCE_JOIN");

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

	public ConversionPattern toPattern(ScvmtConversionTable entity, ConversionInfo info, Join sourceJoin) {
		switch(this) {
			case None:
				return entity.typeNone.toDomain(info, sourceJoin);
			case CodeToId:
				return entity.typeCodeToId.toDomain(info, sourceJoin);
			case CodeToCode:
				return entity.typeCodeToCode.toDomain(info, sourceJoin);
			case FixedValue:
				return entity.typeFixedValue.toDomain(info, sourceJoin);
			case FixedValueWithCondition:
				return entity.typeFixedValueWithCondition.toDomain(info.getDatebaseType().spec(), sourceJoin);
			case Parent:
				return entity.typeParent.toDomain(info, sourceJoin);
			case StringConcat:
				return entity.typeStringConcat.toDomain(info.getDatebaseType().spec(), sourceJoin);
			case TimeWithDayAttr:
				return entity.typeTimeWithDayAttr.toDomain(sourceJoin);
			case DateTimeMerge:
				return entity.typeDateTimeMerge.toDomain(info, sourceJoin);
			case Guid:
				return entity.typeGuid.toDomain(info.getDatebaseType().spec());
			case Password:
				return entity.typePassword.toDomain(info, sourceJoin);
			case FileId:
				return entity.typeFileId.toDomain(info, sourceJoin);
			case SourceJoin:
				return entity.typeSourceJoin.toDomain(info);
		}

		throw new RuntimeException("ConversionPatternが不正です");
	}
}
