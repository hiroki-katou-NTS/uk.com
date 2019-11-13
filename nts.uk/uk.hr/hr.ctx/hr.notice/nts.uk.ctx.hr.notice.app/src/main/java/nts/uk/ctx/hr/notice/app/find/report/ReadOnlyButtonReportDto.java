package nts.uk.ctx.hr.notice.app.find.report;

import lombok.Getter;

@Getter
public class ReadOnlyButtonReportDto extends DataTypeStateReportDto{
	private String readText;

	public ReadOnlyButtonReportDto(String readText) {
		super();
		this.dataTypeValue = DataTypeValueReport.READONLY_BUTTON.value;
		this.readText = readText;
	}

	public static ReadOnlyButtonReportDto createFromJavaType(String readText) {
		return new ReadOnlyButtonReportDto(readText);
	}

}