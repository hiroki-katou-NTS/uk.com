package nts.uk.ctx.pereg.pub.person.info.item;

import lombok.Getter;

@Getter
public class TimeItemExport extends DataTypeStateExport{

	private long max;
	private long min;
	
	private TimeItemExport(long max, long min) {
		super();
		this.dataTypeValue = DataTypeValueExport.TIME.value;
		this.max = max;
		this.min = min;
	}

	public static TimeItemExport createFromJavaType(long max, long min) {
		return new TimeItemExport(max, min);
	}
}
