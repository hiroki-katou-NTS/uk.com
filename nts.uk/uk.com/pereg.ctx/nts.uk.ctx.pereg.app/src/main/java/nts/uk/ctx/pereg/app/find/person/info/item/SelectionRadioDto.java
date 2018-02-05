package nts.uk.ctx.pereg.app.find.person.info.item;

import lombok.Getter;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.CodeNameReferenceType;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.EnumReferenceCondition;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.MasterReferenceCondition;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.ReferenceTypeState;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.ReferenceTypes;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeValue;

@Getter
public class SelectionRadioDto extends DataTypeStateDto {

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

	public SelectionRadioDto(ReferenceTypes referenceType) {
		super();
		this.referenceType = referenceType;
		this.dataTypeValue = DataTypeValue.SELECTION_RADIO.value;
	}

	public static SelectionItemDto createFromJavaType(ReferenceTypeState referenceTypeState) {
		ReferenceTypes refType = referenceTypeState.getReferenceType();

		int dataTypeValue = DataTypeValue.SELECTION_RADIO.value;

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
