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

	public static SelectionItemDto createMasterRefDto(String masterType) {
		return MasterRefConditionDto.createFromJavaType(masterType);
	}

	public static SelectionItemDto createCodeNameRefDto(String typeCode) {
		return CodeNameRefTypeDto.createFromJavaType(typeCode);
	}

	public static SelectionItemDto createEnumRefDto(String enumName) {
		return EnumRefConditionDto.createFromJavaType(enumName);
	}

	public SelectionItemDto(ReferenceTypes referenceType) {
		super();
		this.referenceType = referenceType;
		this.dataTypeValue = DataTypeValue.SELECTION.value;
	}

	public static SelectionItemDto createFromJavaType(ReferenceTypeState referenceTypeState) {
		ReferenceTypes refType = referenceTypeState.getReferenceType();

		if (refType == ReferenceTypes.DESIGNATED_MASTER) {
			MasterReferenceCondition masterRef = (MasterReferenceCondition) referenceTypeState;
			return createMasterRefDto(masterRef.getMasterType().v());
		} else if (refType == ReferenceTypes.CODE_NAME) {
			CodeNameReferenceType codeNameRef = (CodeNameReferenceType) referenceTypeState;
			return createCodeNameRefDto(codeNameRef.getTypeCode().v());
		} else {
			EnumReferenceCondition enumRef = (EnumReferenceCondition) referenceTypeState;
			return createEnumRefDto(enumRef.getEnumName().v());
		}
	}
}
