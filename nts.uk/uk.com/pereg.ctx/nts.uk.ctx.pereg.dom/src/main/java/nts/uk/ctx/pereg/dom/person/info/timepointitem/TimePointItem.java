package nts.uk.ctx.pereg.dom.person.info.timepointitem;

import lombok.Getter;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeState;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeValue;
import nts.uk.shr.com.time.TimeWithDayAttr;

@Getter
public class TimePointItem extends DataTypeState {
	private TimeWithDayAttr timePointItemMin;
	private TimeWithDayAttr timePointItemMax;
	
	private TimePointItem(int timePointItemMin, int timePointItemMax) {
		super();
		this.dataTypeValue = DataTypeValue.TIMEPOINT;
		this.timePointItemMin = new TimeWithDayAttr(timePointItemMin);
		this.timePointItemMax = new TimeWithDayAttr(timePointItemMax);
	}

	public static TimePointItem createFromJavaType(int timePointItemMin, int timePointItemMax) {
		return new TimePointItem(timePointItemMin, timePointItemMax);
	}

}
