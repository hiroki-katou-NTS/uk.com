package nts.uk.ctx.hr.notice.app.find.report;

import lombok.Getter;
@Getter
public class TimePointItemReportDto extends DataTypeStateReportDto{
	private long timePointItemMin;
	private long timePointItemMax;

	private TimePointItemReportDto(int timePointItemMin, int timePointItemMax) {
		super();
		this.dataTypeValue = DataTypeValueReport.TIMEPOINT.value;
		this.timePointItemMin = timePointItemMin;
		this.timePointItemMax = timePointItemMax;
	}

	public static TimePointItemReportDto createFromJavaType(int timePointItemMin, int timePointItemMax) {
		return new TimePointItemReportDto(timePointItemMin, timePointItemMax);
	}
}
