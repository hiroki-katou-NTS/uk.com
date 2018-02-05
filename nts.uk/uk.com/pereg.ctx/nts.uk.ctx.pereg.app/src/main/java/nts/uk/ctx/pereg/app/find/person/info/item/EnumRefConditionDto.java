package nts.uk.ctx.pereg.app.find.person.info.item;

import lombok.Getter;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.ReferenceTypes;

@Getter
public class EnumRefConditionDto extends SelectionItemDto {

	private String enumName;

	private EnumRefConditionDto(String enumName, int dataType) {
		super(ReferenceTypes.ENUM);
		this.dataTypeValue = dataType;
		this.enumName = enumName;
	}

	public static SelectionItemDto createFromJavaType(String enumName, int dataTypeValue) {
		return new EnumRefConditionDto(enumName, dataTypeValue);
	}
}
