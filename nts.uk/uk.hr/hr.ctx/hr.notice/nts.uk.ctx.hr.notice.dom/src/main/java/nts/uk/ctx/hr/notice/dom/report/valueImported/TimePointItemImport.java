package nts.uk.ctx.hr.notice.dom.report.valueImported;

import lombok.Getter;

@Getter
public class TimePointItemImport extends DataTypeStateImport {

	private long timePointItemMin;
	private long timePointItemMax;

	private TimePointItemImport(int timePointItemMin, int timePointItemMax) {
		super();
		this.dataTypeValue = DataTypeValueImport.TIMEPOINT.value;
		this.timePointItemMin = timePointItemMin;
		this.timePointItemMax = timePointItemMax;
	}

	public static TimePointItemImport createFromJavaType(int timePointItemMin, int timePointItemMax) {
		return new TimePointItemImport(timePointItemMin, timePointItemMax);
	}
}
