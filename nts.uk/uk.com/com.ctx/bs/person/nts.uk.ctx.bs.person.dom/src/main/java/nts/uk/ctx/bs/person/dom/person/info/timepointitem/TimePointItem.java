package nts.uk.ctx.bs.person.dom.person.info.timepointitem;

import lombok.Getter;
import nts.uk.ctx.bs.person.dom.person.info.singleitem.DataTypeState;
import nts.uk.ctx.bs.person.dom.person.info.singleitem.DataTypeValue;

@Getter
public class TimePointItem extends DataTypeState {
	private TimeWithDayAttr timePointItemMin;
	private TimeWithDayAttr timePointItemMax;

	private TimePointItem(long timePointItemMin, long timePointItemMax) {
		super();
		this.dataTypeValue = DataTypeValue.TIMEPOINT;
		this.timePointItemMin = new TimeWithDayAttr(timePointItemMin);
		this.timePointItemMax = new TimeWithDayAttr(timePointItemMax);
	}

	public static TimePointItem createFromJavaType(long timePointItemMin, long timePointItemMax) {
		return new TimePointItem(timePointItemMin, timePointItemMax);
	}

}
