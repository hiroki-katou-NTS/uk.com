package nts.uk.ctx.pereg.pub.person.info.item;

import lombok.Getter;

@Getter
public class SelectionItemExport extends DataTypeStateExport {

	private ReferenceTypesExport referenceType;

	public static SelectionItemExport createMasterRefDto(String masterType, int dataTypeValue) {
		return MasterRefConditionExport.createFromJavaType(masterType, dataTypeValue);
	}

	public static SelectionItemExport createCodeNameRefDto(String typeCode, int dataTypeValue) {
		return CodeNameRefTypeExport.createFromJavaType(typeCode, dataTypeValue);
	}

	public static SelectionItemExport createEnumRefDto(String enumName, int dataTypeValue) {
		return EnumRefConditionExport.createFromJavaType(enumName, dataTypeValue);
	}

	public SelectionItemExport(ReferenceTypesExport referenceType) {
		super();
		this.referenceType = referenceType;
		this.dataTypeValue = DataTypeValueExport.SELECTION.value;
	}
}
