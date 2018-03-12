package nts.uk.ctx.pereg.app.find.person.info.item;

import lombok.Getter;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.ReferenceTypes;

@Getter
public class MasterRefConditionDto extends SelectionItemDto {

	private String masterType;

	private MasterRefConditionDto(String masterType, int dataTypeValue) {
		super(ReferenceTypes.DESIGNATED_MASTER);
		this.masterType = masterType;
		this.dataTypeValue = dataTypeValue;
	}

	public static SelectionItemDto createFromJavaType(String masterType, int dataTypeValue) {
		return new MasterRefConditionDto(masterType, dataTypeValue);
	}
}
