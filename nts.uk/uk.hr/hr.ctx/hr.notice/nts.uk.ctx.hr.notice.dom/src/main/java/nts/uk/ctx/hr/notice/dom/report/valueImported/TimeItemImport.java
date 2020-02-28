package nts.uk.ctx.hr.notice.dom.report.valueImported;

import lombok.Getter;

@Getter
public class TimeItemImport extends DataTypeStateImport{

	private long max;
	private long min;
	
	private TimeItemImport(long max, long min) {
		super();
		this.dataTypeValue = DataTypeValueImport.TIME.value;
		this.max = max;
		this.min = min;
	}

	public static TimeItemImport createFromJavaType(long max, long min) {
		return new TimeItemImport(max, min);
	}
}
