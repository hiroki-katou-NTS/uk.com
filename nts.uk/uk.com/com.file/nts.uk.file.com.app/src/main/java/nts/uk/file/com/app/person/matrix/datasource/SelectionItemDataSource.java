package nts.uk.file.com.app.person.matrix.datasource;

import lombok.Getter;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.CodeNameReferenceType;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.EnumReferenceCondition;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.MasterReferenceCondition;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.ReferenceTypeState;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.ReferenceTypes;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeValue;
@Getter
public class SelectionItemDataSource extends DataTypeStateDataSource {

	private ReferenceTypes referenceType;

	public static SelectionItemDataSource createMasterRefDto(String masterType, int dataTypeValue) {
		return MasterRefConditionDataSource.createFromJavaType(masterType, dataTypeValue);
	}

	public static SelectionItemDataSource createCodeNameRefDto(String typeCode, int dataTypeValue) {
		return CodeNameRefTypeDataSource.createFromJavaType(typeCode, dataTypeValue);
	}

	public static SelectionItemDataSource createEnumRefDto(String enumName, int dataTypeValue) {
		return EnumRefConditionDataSource.createFromJavaType(enumName, dataTypeValue);
	}

	public SelectionItemDataSource(ReferenceTypes referenceType) {
		super();
		this.referenceType = referenceType;
		this.dataTypeValue = DataTypeValue.SELECTION.value;
	}

	public static SelectionItemDataSource createFromJavaType(ReferenceTypeState referenceTypeState, int dataTypeValue) {
		ReferenceTypes refType = referenceTypeState.getReferenceType();

		if (refType == ReferenceTypes.DESIGNATED_MASTER) {
			MasterReferenceCondition masterRef = (MasterReferenceCondition) referenceTypeState;
			return createMasterRefDto(masterRef.getMasterType().v(), dataTypeValue);
		} else if (refType == ReferenceTypes.CODE_NAME) {
			CodeNameReferenceType codeNameRef = (CodeNameReferenceType) referenceTypeState;
			return createCodeNameRefDto(codeNameRef.getTypeCode().v(), dataTypeValue);
		} else {
			EnumReferenceCondition enumRef = (EnumReferenceCondition) referenceTypeState;
			return createEnumRefDto(enumRef.getEnumName().v(), dataTypeValue);
		}
	}
}