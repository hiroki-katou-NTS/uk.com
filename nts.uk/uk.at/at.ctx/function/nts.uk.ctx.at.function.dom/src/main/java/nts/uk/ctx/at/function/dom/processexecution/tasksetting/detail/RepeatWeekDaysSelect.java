package nts.uk.ctx.at.function.dom.processexecution.tasksetting.detail;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * 繰り返す曜日
 */
@Getter
@AllArgsConstructor
public class RepeatWeekDaysSelect extends DomainObject {
	/* 月曜日 */
	private boolean monday;
	
	/* 木曜日 */
	private boolean tuesday;
	
	/* 水曜日 */
	private boolean wednesday;
	
	/* 火曜日 */
	private boolean thursday;
	
	/* 金曜日 */
	private boolean friday;
	
	/* 土曜日 */
	private boolean saturday;
	
	/* 日曜日 */
	private boolean sunday;
	
	public boolean isCheckedAtLeastOne() {
		return (monday || tuesday || wednesday || thursday || friday || saturday || sunday);
	}
	
	public static RepeatWeekDaysSelect initDefault() {
		return new RepeatWeekDaysSelect(false, false, false, false, false, false, false);
	}
	
	public List<DayOfWeek> setWeekDaysList() {
		List<DayOfWeek> weekDays = new ArrayList<>();
		if (sunday) weekDays.add(DayOfWeek.SUNDAY);
		if (monday) weekDays.add(DayOfWeek.MONDAY);
		if (tuesday) weekDays.add(DayOfWeek.TUESDAY);
		if (wednesday) weekDays.add(DayOfWeek.WEDNESDAY);
		if (thursday) weekDays.add(DayOfWeek.THURSDAY);
		if (friday) weekDays.add(DayOfWeek.FRIDAY);
		if (saturday) weekDays.add(DayOfWeek.SATURDAY);
		return weekDays;
	}
}
