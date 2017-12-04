package nts.uk.ctx.pereg.app.find.person.info.item;

import lombok.Getter;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.ReferenceTypes;

@Getter
public class CodeNameRefTypeDto extends SelectionItemDto {

	private String typeCode;
	private String selectionItemName;

	private CodeNameRefTypeDto(String typeCode) {
		super(ReferenceTypes.CODE_NAME);
		this.typeCode = typeCode;
	}
	
	private CodeNameRefTypeDto(String typeCode, String selectionItemName) {
		super(ReferenceTypes.CODE_NAME);
		this.typeCode = typeCode;
		this.selectionItemName = selectionItemName;
	}

	public static CodeNameRefTypeDto createFromJavaType(String typeCode) {
		return new CodeNameRefTypeDto(typeCode);
	}
	public static CodeNameRefTypeDto createFromJavaType(String typeCode,  String selectionItemName) {
		return new CodeNameRefTypeDto(typeCode, selectionItemName);
	}
}
