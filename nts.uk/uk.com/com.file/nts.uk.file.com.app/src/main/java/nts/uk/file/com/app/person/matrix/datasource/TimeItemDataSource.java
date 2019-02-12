package nts.uk.file.com.app.person.matrix.datasource;

import lombok.Getter;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeValue;

/**
 * TimeItemDataSource
 * 
 * @author lanlt
 *
 */
@Getter
public class TimeItemDataSource extends DataTypeStateDataSource {

	private long max;
	private long min;

	private TimeItemDataSource(long max, long min) {
		super();
		this.dataTypeValue = DataTypeValue.TIME.value;
		this.max = max;
		this.min = min;
	}

	public static TimeItemDataSource createFromJavaType(long max, long min) {
		return new TimeItemDataSource(max, min);
	}
}
