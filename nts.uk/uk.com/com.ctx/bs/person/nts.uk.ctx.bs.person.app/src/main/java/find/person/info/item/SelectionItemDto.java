package find.person.info.item;

import lombok.Getter;
import nts.uk.ctx.bs.person.dom.person.info.selectionitem.CodeNameReferenceType;
import nts.uk.ctx.bs.person.dom.person.info.selectionitem.EnumReferenceCondition;
import nts.uk.ctx.bs.person.dom.person.info.selectionitem.MasterReferenceCondition;
import nts.uk.ctx.bs.person.dom.person.info.selectionitem.ReferenceType;
import nts.uk.ctx.bs.person.dom.person.info.selectionitem.ReferenceTypeState;
import nts.uk.ctx.bs.person.dom.person.info.singleitem.DataTypeValue;

@Getter
public class SelectionItemDto extends DataTypeStateDto {

	private ReferenceType referenceType;

	private static SelectionItemDto createMasterRefDto(String masterType) {
		return MasterRefConditionDto.createFromJavaType(masterType);
	}

	private static SelectionItemDto createCodeNameRefDto(String typeCode) {
		return CodeNameRefTypeDto.createFromJavaType(typeCode);
	}

	private static SelectionItemDto createEnumRefDto(String enumName) {
		return EnumRefConditionDto.createFromJavaType(enumName);
	}

	protected SelectionItemDto(ReferenceType referenceType) {
		super();
		this.referenceType = referenceType;
		this.dataTypeValue = DataTypeValue.SELECTION.value;
	}

	public static SelectionItemDto createFromJavaType(ReferenceTypeState referenceTypeState) {
		ReferenceType refType = referenceTypeState.getReferenceType();

		if (refType == ReferenceType.DESIGNATED_MASTER) {
			MasterReferenceCondition masterRef = (MasterReferenceCondition) referenceTypeState;
			return createMasterRefDto(masterRef.getMasterType().v());
		} else if (refType == ReferenceType.CODE_NAME) {
			CodeNameReferenceType codeNameRef = (CodeNameReferenceType) referenceTypeState;
			return createCodeNameRefDto(codeNameRef.getTypeCode().v());
		} else {
			EnumReferenceCondition enumRef = (EnumReferenceCondition) referenceTypeState;
			return createEnumRefDto(enumRef.getEnumName().v());
		}
	}
}
