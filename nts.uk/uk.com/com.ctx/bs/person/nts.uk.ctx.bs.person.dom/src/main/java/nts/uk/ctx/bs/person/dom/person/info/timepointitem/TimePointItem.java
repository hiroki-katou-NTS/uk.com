package nts.uk.ctx.bs.person.dom.person.info.timepointitem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.bs.person.dom.person.info.singleitem.DataTypeObject;
import nts.uk.ctx.bs.person.dom.person.info.singleitem.DataTypeState;

@AllArgsConstructor
@Getter
public class TimePointItem extends DataTypeObject {
	private ItemDefTimePoint timePointItemMin;
	private ItemDefTimePoint timePointItemMax;

	public TimePointItem(int dayTypeMin, long timePointMin, int dayTypeMax, long timePointMax) {
		super();
		this.dataTypeState = DataTypeState.TIMEPOINT;
		this.timePointItemMin = ItemDefTimePoint.createFromJavaType(dayTypeMin, timePointMin);
		this.timePointItemMax = ItemDefTimePoint.createFromJavaType(dayTypeMax, timePointMax);
	}

}
