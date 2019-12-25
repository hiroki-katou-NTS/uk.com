package nts.uk.ctx.hr.notice.dom.report.valueImported;

import lombok.Getter;

@Getter
public class SelectionItemImport extends DataTypeStateImport {

	private ReferenceTypesImport referenceType;

	public static SelectionItemImport createMasterRefDto(String masterType, int dataTypeValue) {
		return MasterRefConditionImport.createFromJavaType(masterType, dataTypeValue);
	}

	public static SelectionItemImport createCodeNameRefDto(String typeCode, int dataTypeValue) {
		return CodeNameRefTypeImport.createFromJavaType(typeCode, dataTypeValue);
	}

	public static SelectionItemImport createEnumRefDto(String enumName, int dataTypeValue) {
		return EnumRefConditionImport.createFromJavaType(enumName, dataTypeValue);
	}

	public SelectionItemImport(ReferenceTypesImport referenceType) {
		super();
		this.referenceType = referenceType;
		this.dataTypeValue = DataTypeValueImport.SELECTION.value;
	}
}
