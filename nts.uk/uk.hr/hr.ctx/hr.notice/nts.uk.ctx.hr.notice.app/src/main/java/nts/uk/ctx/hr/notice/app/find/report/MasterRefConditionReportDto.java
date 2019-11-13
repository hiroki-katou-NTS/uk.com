package nts.uk.ctx.hr.notice.app.find.report;

public class MasterRefConditionReportDto extends SelectionItemReportDto {
	private String masterType;

	private MasterRefConditionReportDto(String masterType, int dataTypeValue) {
		super(ReferenceTypesReport.DESIGNATED_MASTER);
		this.masterType = masterType;
		this.dataTypeValue = dataTypeValue;
	}

	public static SelectionItemReportDto createFromJavaType(String masterType, int dataTypeValue) {
		return new MasterRefConditionReportDto(masterType, dataTypeValue);
	}
}
