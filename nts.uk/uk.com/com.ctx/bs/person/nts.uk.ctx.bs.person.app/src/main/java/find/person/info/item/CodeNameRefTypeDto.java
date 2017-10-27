package find.person.info.item;

import lombok.Getter;
import nts.uk.ctx.bs.person.dom.person.info.selectionitem.ReferenceTypes;

@Getter
public class CodeNameRefTypeDto extends SelectionItemDto {

	private String typeCode;

	private CodeNameRefTypeDto(String typeCode) {
		super(ReferenceTypes.CODE_NAME);
		this.typeCode = typeCode;
	}

	public static CodeNameRefTypeDto createFromJavaType(String typeCode) {
		return new CodeNameRefTypeDto(typeCode);
	}
}
