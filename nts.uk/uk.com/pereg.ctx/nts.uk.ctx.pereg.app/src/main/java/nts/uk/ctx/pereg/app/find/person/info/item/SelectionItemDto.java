package nts.uk.ctx.pereg.app.find.person.info.item;

import lombok.Getter;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.CodeNameReferenceType;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.EnumReferenceCondition;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.MasterReferenceCondition;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.ReferenceTypeState;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.ReferenceTypes;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeValue;

@Getter
public class SelectionItemDto extends DataTypeStateDto {

	private ReferenceTypes referenceType;

	public static SelectionItemDto createMasterRefDto(String masterType, int dataTypeValue) {
		return MasterRefConditionDto.createFromJavaType(masterType, dataTypeValue);
	}

	public static SelectionItemDto createCodeNameRefDto(String typeCode, int dataTypeValue) {
		return CodeNameRefTypeDto.createFromJavaType(typeCode, dataTypeValue);
	}

	public static SelectionItemDto createEnumRefDto(String enumName, int dataTypeValue) {
		return EnumRefConditionDto.createFromJavaType(enumName, dataTypeValue);
	}

	public SelectionItemDto(ReferenceTypes referenceType) {
		super();
		this.referenceType = referenceType;
		this.dataTypeValue = DataTypeValue.SELECTION.value;
	}

	public static SelectionItemDto createFromJavaType(ReferenceTypeState referenceTypeState, int dataTypeValue) {
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
