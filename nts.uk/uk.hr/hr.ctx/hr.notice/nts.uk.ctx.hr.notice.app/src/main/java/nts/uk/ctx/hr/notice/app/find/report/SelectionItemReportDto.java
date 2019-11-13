package nts.uk.ctx.hr.notice.app.find.report;

import lombok.Getter;

@Getter
public class SelectionItemReportDto extends DataTypeStateReportDto{
	
	private ReferenceTypesReport referenceType;

	public static SelectionItemReportDto createMasterRefDto(String masterType, int dataTypeValue) {
		return MasterRefConditionReportDto.createFromJavaType(masterType, dataTypeValue);
	}

	public static SelectionItemReportDto createCodeNameRefDto(String typeCode, int dataTypeValue) {
		return CodeNameRefTypeReportDto.createFromJavaType(typeCode, dataTypeValue);
	}

	public static SelectionItemReportDto createEnumRefDto(String enumName, int dataTypeValue) {
		return EnumRefConditionReportDto.createFromJavaType(enumName, dataTypeValue);
	}

	public SelectionItemReportDto(ReferenceTypesReport referenceType) {
		super();
		this.referenceType = referenceType;
		this.dataTypeValue = DataTypeValueReport.SELECTION.value;
	}

//	public static SelectionItemReportDto createFromJavaType(ReferenceTypesReport referenceTypeState, int dataTypeValue) {
//		ReferenceTypesReport refType = referenceTypeState.getReferenceType();
//
//		if (refType == ReferenceTypesReport.DESIGNATED_MASTER) {
//			MasterReferenceCondition masterRef = (MasterReferenceCondition) referenceTypeState;
//			return createMasterRefDto(masterRef.getMasterType().v(), dataTypeValue);
//		} else if (refType == ReferenceTypesReport.CODE_NAME) {
//			CodeNameReferenceType codeNameRef = (CodeNameReferenceType) referenceTypeState;
//			return createCodeNameRefDto(codeNameRef.getTypeCode().v(), dataTypeValue);
//		} else {
//			EnumReferenceCondition enumRef = (EnumReferenceCondition) referenceTypeState;
//			return createEnumRefDto(enumRef.getEnumName().v(), dataTypeValue);
//		}
//	}
}
