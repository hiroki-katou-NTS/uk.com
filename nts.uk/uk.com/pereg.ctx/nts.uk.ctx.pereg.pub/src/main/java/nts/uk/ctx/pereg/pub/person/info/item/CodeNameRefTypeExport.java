package nts.uk.ctx.pereg.pub.person.info.item;

import lombok.Getter;

@Getter
public class CodeNameRefTypeExport extends SelectionItemExport {

	private String typeCode;
	private String selectionItemName;

	private CodeNameRefTypeExport(String typeCode, String selectionItemName) {
		super(ReferenceTypesExport.CODE_NAME);
		this.typeCode = typeCode;
		this.selectionItemName = selectionItemName;
	}

	private CodeNameRefTypeExport(String typeCode, int dataTypeValue) {
		super(ReferenceTypesExport.CODE_NAME);
		this.typeCode = typeCode;
		this.dataTypeValue = dataTypeValue;
	}

	public static CodeNameRefTypeExport createFromJavaType(String typeCode, String selectionItemName) {
		return new CodeNameRefTypeExport(typeCode, selectionItemName);
	}

	public static SelectionItemExport createFromJavaType(String typeCode, int dataTypeValue) {
		return new CodeNameRefTypeExport(typeCode, dataTypeValue);
	}
}
