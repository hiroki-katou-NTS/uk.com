package nts.uk.ctx.hr.notice.dom.report.valueImported;

import lombok.Getter;

@Getter
public class CodeNameRefTypeImport extends SelectionItemImport {

	private String typeCode;
	private String selectionItemName;

	private CodeNameRefTypeImport(String typeCode, String selectionItemName) {
		super(ReferenceTypesImport.CODE_NAME);
		this.typeCode = typeCode;
		this.selectionItemName = selectionItemName;
	}

	private CodeNameRefTypeImport(String typeCode, int dataTypeValue) {
		super(ReferenceTypesImport.CODE_NAME);
		this.typeCode = typeCode;
		this.dataTypeValue = dataTypeValue;
	}

	public static CodeNameRefTypeImport createFromJavaType(String typeCode, String selectionItemName) {
		return new CodeNameRefTypeImport(typeCode, selectionItemName);
	}

	public static SelectionItemImport createFromJavaType(String typeCode, int dataTypeValue) {
		return new CodeNameRefTypeImport(typeCode, dataTypeValue);
	}
}
