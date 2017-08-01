package nts.uk.ctx.bs.person.dom.person.info.timepointitem;

import lombok.Getter;
import nts.uk.ctx.bs.person.dom.person.info.singleitem.DataTypeState;
import nts.uk.ctx.bs.person.dom.person.info.singleitem.DataTypeValue;

@Getter
public class TimePointItem extends DataTypeState {
	private ItemDefTimePoint timePointItemMin;
	private ItemDefTimePoint timePointItemMax;

	private TimePointItem(int dayTypeMin, long timePointMin, int dayTypeMax, long timePointMax) {
		super();
		this.dataTypeValue = DataTypeValue.TIMEPOINT;
		this.timePointItemMin = ItemDefTimePoint.createFromJavaType(dayTypeMin, timePointMin);
		this.timePointItemMax = ItemDefTimePoint.createFromJavaType(dayTypeMax, timePointMax);
	}

	public static TimePointItem createFromJavaType(int dayTypeMin, long timePointMin, int dayTypeMax,
			long timePointMax) {
		return new TimePointItem(dayTypeMin, timePointMin, dayTypeMax, timePointMax);
	}

}
