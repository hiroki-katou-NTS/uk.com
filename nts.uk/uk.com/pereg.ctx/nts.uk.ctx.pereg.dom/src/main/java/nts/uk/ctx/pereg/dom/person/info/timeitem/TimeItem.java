package nts.uk.ctx.pereg.dom.person.info.timeitem;

import lombok.Getter;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeState;
import nts.uk.ctx.pereg.dom.person.info.singleitem.DataTypeValue;

@Getter
public class TimeItem extends DataTypeState {
	private TimeItemMax max;
	private TimeItemMin min;

	private TimeItem(int max, int min) {
		super();
		this.dataTypeValue = DataTypeValue.TIME;
		this.max = new TimeItemMax(max);
		this.min = new TimeItemMin(min);
	}

	public static TimeItem createFromJavaType(int max, int min) {
		return new TimeItem(max, min);
	}
}
