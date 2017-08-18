package find.person.info.item;

import lombok.Getter;
import nts.uk.ctx.bs.person.dom.person.info.selectionitem.ReferenceTypes;

@Getter
public class EnumRefConditionDto extends SelectionItemDto {

	private String enumName;
	
	private EnumRefConditionDto(String enumName) {
		super(ReferenceTypes.ENUM);
		this.enumName = enumName;
	}

	public static EnumRefConditionDto createFromJavaType(String enumName) {
		return new EnumRefConditionDto(enumName);
	}
}
