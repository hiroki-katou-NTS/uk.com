package nts.uk.ctx.hr.notice.app.find.report;

import lombok.Getter;

@Getter
public class DateItemReportDto extends DataTypeStateReportDto{
	
	private int dateItemType;

	private DateItemReportDto(int dateItemType) {
		super();
		this.dataTypeValue = DataTypeValueReport.DATE.value;
		this.dateItemType = dateItemType;
	}

	public static DateItemReportDto createFromJavaType(int dateItemType) {
		return new DateItemReportDto(dateItemType);
	}
}
