package nts.uk.ctx.pereg.app.find.person.info.item;

import lombok.Getter;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeValue;

@Getter
public class TimePointItemDto extends DataTypeStateDto {

	private long timePointItemMin;
	private long timePointItemMax;

	private TimePointItemDto(int timePointItemMin, int timePointItemMax) {
		super();
		this.dataTypeValue = DataTypeValue.TIMEPOINT.value;
		this.timePointItemMin = timePointItemMin;
		this.timePointItemMax = timePointItemMax;
	}

	public static TimePointItemDto createFromJavaType(int timePointItemMin, int timePointItemMax) {
		return new TimePointItemDto(timePointItemMin, timePointItemMax);
	}
}
