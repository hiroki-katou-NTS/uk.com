package nts.uk.ctx.hr.notice.app.find.report;

import lombok.Getter;

@Getter
public class ReadOnlyReportDto extends DataTypeStateReportDto {

	private String readText;

	public ReadOnlyReportDto(String readText) {
		super();
		this.dataTypeValue = DataTypeValueReport.READONLY.value;
		this.readText = readText;
	}

	public static ReadOnlyReportDto createFromJavaType(String readText) {
		return new ReadOnlyReportDto(readText);
	}

}
