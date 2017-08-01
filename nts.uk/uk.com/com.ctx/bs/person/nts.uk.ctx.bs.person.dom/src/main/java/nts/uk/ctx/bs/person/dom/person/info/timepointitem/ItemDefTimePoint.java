package nts.uk.ctx.bs.person.dom.person.info.timepointitem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;

@AllArgsConstructor
@Getter
public class ItemDefTimePoint extends AggregateRoot {
	private DayType dayType;
	private TimePoint timePoint;

	public static ItemDefTimePoint createFromJavaType(int dayType, long timePoint) {
		return new ItemDefTimePoint(EnumAdaptor.valueOf(dayType, DayType.class), new TimePoint(timePoint));
	}
}
