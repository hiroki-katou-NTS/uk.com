package find.person.info.item;

import lombok.Getter;
import nts.uk.ctx.bs.person.dom.person.info.selectionitem.ReferenceType;

@Getter
public class CodeNameRefTypeDto extends ReferenceTypeStateDto {

	private String typeCode;

	private CodeNameRefTypeDto(String typeCode) {
		super();
		this.referenceType = ReferenceType.CODE_NAME.value;
		this.typeCode = typeCode;
	}

	public static CodeNameRefTypeDto createFromJavaType(String typeCode) {
		return new CodeNameRefTypeDto(typeCode);
	}
}
