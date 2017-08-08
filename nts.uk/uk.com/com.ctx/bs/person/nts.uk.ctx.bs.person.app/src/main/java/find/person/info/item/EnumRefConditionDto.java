package find.person.info.item;

import lombok.Getter;
import nts.uk.ctx.bs.person.dom.person.info.selectionitem.ReferenceType;

@Getter
public class EnumRefConditionDto extends ReferenceTypeStateDto {

	private String enumName;
	
	private EnumRefConditionDto(String enumName) {
		super();
		this.referenceType = ReferenceType.ENUM.value;
		this.enumName = enumName;
	}

	public static EnumRefConditionDto createFromJavaType(String enumName) {
		return new EnumRefConditionDto(enumName);
	}
}
