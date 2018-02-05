package nts.uk.ctx.pereg.app.find.person.info.item;

import lombok.Getter;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.ReferenceTypes;

@Getter
public class MasterRefConditionDto extends SelectionItemDto {

	private String masterType;

	private MasterRefConditionDto(String masterType) {
		super(ReferenceTypes.DESIGNATED_MASTER);
		this.masterType = masterType;
	}

	private MasterRefConditionDto(String masterType, int dataTypeValue) {
		super(ReferenceTypes.DESIGNATED_MASTER);
		this.masterType = masterType;
		this.dataTypeValue = dataTypeValue;
	}

	public static MasterRefConditionDto createFromJavaType(String masterType) {
		return new MasterRefConditionDto(masterType);
	}

	public static SelectionItemDto createFromJavaType(String masterType, int dataTypeValue) {
		return new MasterRefConditionDto(masterType, dataTypeValue);
	}
}
