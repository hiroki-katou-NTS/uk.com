package nts.uk.ctx.pereg.app.find.person.info.item;

import lombok.Getter;
import nts.uk.ctx.pereg.dom.person.info.selectionitem.ReferenceTypes;

@Getter
public class CodeNameRefTypeDto extends SelectionItemDto {

	private String typeCode;
	private String selectionItemName;

	private CodeNameRefTypeDto(String typeCode, String selectionItemName) {
		super(ReferenceTypes.CODE_NAME);
		this.typeCode = typeCode;
		this.selectionItemName = selectionItemName;
	}

	private CodeNameRefTypeDto(String typeCode, int dataTypeValue) {
		super(ReferenceTypes.CODE_NAME);
		this.typeCode = typeCode;
		this.dataTypeValue = dataTypeValue;
	}

	public static CodeNameRefTypeDto createFromJavaType(String typeCode, String selectionItemName) {
		return new CodeNameRefTypeDto(typeCode, selectionItemName);
	}

	public static SelectionItemDto createFromJavaType(String typeCode, int dataTypeValue) {
		return new CodeNameRefTypeDto(typeCode, dataTypeValue);
	}
}
