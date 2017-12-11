package nts.uk.ctx.pereg.app.find.person.info.item;

import lombok.Getter;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeValue;

@Getter
public class TimeItemDto extends DataTypeStateDto{

	private long max;
	private long min;
	
	private TimeItemDto(long max, long min) {
		super();
		this.dataTypeValue = DataTypeValue.TIME.value;
		this.max = max;
		this.min = min;
	}

	public static TimeItemDto createFromJavaType(long max, long min) {
		return new TimeItemDto(max, min);
	}
}
