package nts.uk.ctx.bs.person.dom.person.info.timeitem;

import lombok.Getter;
import nts.uk.ctx.bs.person.dom.person.info.singleitem.DataTypeObject;
import nts.uk.ctx.bs.person.dom.person.info.singleitem.DataTypeState;

@Getter
public class TimeItem extends DataTypeObject {
	private TimeItemMax max;
	private TimeItemMin min;

	public TimeItem(long max, long min) {
		super();
		this.dataTypeState = DataTypeState.TIME;
		this.max = new TimeItemMax(max);
		this.min = new TimeItemMin(min);
	}
}
