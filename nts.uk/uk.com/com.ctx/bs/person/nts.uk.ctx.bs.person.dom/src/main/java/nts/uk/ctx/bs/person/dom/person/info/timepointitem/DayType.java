package nts.uk.ctx.bs.person.dom.person.info.timepointitem;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DayType {
	// 1:前日(Previous day)
	PREVIOUS_DAY(1),

	// 2:当日(Day)
	DAY(2),

	// 3:翌日(Next day)
	NEXT_DAY(3),

	// 4:翌々日(Two days later)
	TWO_DAYS_LATER(4);

	public final int value;
}
