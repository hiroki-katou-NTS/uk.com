package nts.uk.ctx.pereg.pub.person.info.item;

import lombok.Getter;

@Getter
public class TimePointItemExport extends DataTypeStateExport {

	private long timePointItemMin;
	private long timePointItemMax;

	private TimePointItemExport(int timePointItemMin, int timePointItemMax) {
		super();
		this.dataTypeValue = DataTypeValueExport.TIMEPOINT.value;
		this.timePointItemMin = timePointItemMin;
		this.timePointItemMax = timePointItemMax;
	}

	public static TimePointItemExport createFromJavaType(int timePointItemMin, int timePointItemMax) {
		return new TimePointItemExport(timePointItemMin, timePointItemMax);
	}
}
