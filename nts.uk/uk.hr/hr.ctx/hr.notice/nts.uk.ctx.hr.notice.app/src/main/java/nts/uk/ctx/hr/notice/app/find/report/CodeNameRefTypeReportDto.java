package nts.uk.ctx.hr.notice.app.find.report;

import lombok.Getter;

@Getter
public class CodeNameRefTypeReportDto extends SelectionItemReportDto{

	private String typeCode;
	private String selectionItemName;

	private CodeNameRefTypeReportDto(String typeCode, String selectionItemName) {
		super(ReferenceTypesReport.CODE_NAME);
		this.typeCode = typeCode;
		this.selectionItemName = selectionItemName;
	}

	private CodeNameRefTypeReportDto(String typeCode, int dataTypeValue) {
		super(ReferenceTypesReport.CODE_NAME);
		this.typeCode = typeCode;
		this.dataTypeValue = dataTypeValue;
	}

	public static CodeNameRefTypeReportDto createFromJavaType(String typeCode, String selectionItemName) {
		return new CodeNameRefTypeReportDto(typeCode, selectionItemName);
	}

	public static SelectionItemReportDto createFromJavaType(String typeCode, int dataTypeValue) {
		return new CodeNameRefTypeReportDto(typeCode, dataTypeValue);
	}
}
