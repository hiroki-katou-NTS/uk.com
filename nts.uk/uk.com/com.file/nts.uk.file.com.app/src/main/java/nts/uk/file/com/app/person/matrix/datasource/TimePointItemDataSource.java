package nts.uk.file.com.app.person.matrix.datasource;

import lombok.Getter;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeValue;
@Getter
public class TimePointItemDataSource extends DataTypeStateDataSource {

	private long timePointItemMin;
	private long timePointItemMax;

	private TimePointItemDataSource(int timePointItemMin, int timePointItemMax) {
		super();
		this.dataTypeValue = DataTypeValue.TIMEPOINT.value;
		this.timePointItemMin = timePointItemMin;
		this.timePointItemMax = timePointItemMax;
	}

	public static TimePointItemDataSource createFromJavaType(int timePointItemMin, int timePointItemMax) {
		return new TimePointItemDataSource(timePointItemMin, timePointItemMax);
	}
}
