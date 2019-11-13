package nts.uk.ctx.hr.notice.app.find.report;

import lombok.Getter;

@Getter
public class TimeItemReportDto extends DataTypeStateReportDto{
	private long max;
	private long min;
	
	private TimeItemReportDto(long max, long min) {
		super();
		this.dataTypeValue = DataTypeValueReport.TIME.value;
		this.max = max;
		this.min = min;
	}

	public static TimeItemReportDto createFromJavaType(long max, long min) {
		return new TimeItemReportDto(max, min);
	}
}
