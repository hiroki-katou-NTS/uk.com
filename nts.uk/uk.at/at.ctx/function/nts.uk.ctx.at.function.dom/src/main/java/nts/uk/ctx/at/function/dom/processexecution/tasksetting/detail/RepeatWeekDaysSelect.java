package nts.uk.ctx.at.function.dom.processexecution.tasksetting.detail;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 繰り返す曜日
 */
@Getter
@Builder
@AllArgsConstructor
public class RepeatWeekDaysSelect extends DomainObject {
	private static final String[] KANJI_STRING = new String[] { "月曜日", " 木曜日", "水曜日", "火曜日", "金曜日", "土曜日", "日曜日" };

	/*
	 * 月曜日
	 */
	private NotUseAtr monday;

	/*
	 * 木曜日
	 */
	private NotUseAtr tuesday;

	/*
	 * 水曜日
	 */
	private NotUseAtr wednesday;

	/*
	 * 火曜日
	 */
	private NotUseAtr thursday;

	/*
	 * 金曜日
	 */
	private NotUseAtr friday;

	/*
	 * 土曜日
	 */
	private NotUseAtr saturday;

	/*
	 * 日曜日
	 */
	private NotUseAtr sunday;

	@Getter(value = AccessLevel.NONE)
	private Map<Integer, Boolean> values;

	public RepeatWeekDaysSelect(boolean monday, boolean tuesday, boolean wednesday, boolean thursday, boolean friday,
			boolean saturday, boolean sunday) {
		this.monday = monday ? NotUseAtr.USE : NotUseAtr.NOT_USE;
		this.tuesday = tuesday ? NotUseAtr.USE : NotUseAtr.NOT_USE;
		this.wednesday = wednesday ? NotUseAtr.USE : NotUseAtr.NOT_USE;
		this.thursday = thursday ? NotUseAtr.USE : NotUseAtr.NOT_USE;
		this.friday = friday ? NotUseAtr.USE : NotUseAtr.NOT_USE;
		this.saturday = saturday ? NotUseAtr.USE : NotUseAtr.NOT_USE;
		this.sunday = sunday ? NotUseAtr.USE : NotUseAtr.NOT_USE;
	}

	public boolean isCheckedAtLeastOne() {
		if (values == null) {
			values = generateValuesMap();
		}
		return !values.values().isEmpty();
	}

	public List<DayOfWeek> setWeekDaysList() {
		if (values == null) {
			values = generateValuesMap();
		}
		return values.keySet().stream().map(DayOfWeek::of).collect(Collectors.toList());
	}

	public static boolean isTrue(NotUseAtr atr) {
		return atr.equals(NotUseAtr.USE);
	}

	private Map<Integer, Boolean> generateValuesMap() {
		Map<Integer, Boolean> values = new HashMap<>();
		values.put(1, isTrue(monday));
		values.put(2, isTrue(tuesday));
		values.put(3, isTrue(wednesday));
		values.put(4, isTrue(thursday));
		values.put(5, isTrue(friday));
		values.put(6, isTrue(saturday));
		values.put(7, isTrue(sunday));
		return values.entrySet().stream().filter(Entry::getValue)
				.collect(Collectors.toMap(Entry::getKey, Entry::getValue));
	}
	
	public String getTrueDayString() {
		if (values == null) {
			values = generateValuesMap();
		}
		StringBuilder res = new StringBuilder();
		for (int i = 0; i < values.size(); i++) {
			res.append(KANJI_STRING[values.keySet().toArray(new Integer[0])[i] - 1]);
			if (i != values.size() - 1) {
				res.append(", ");
			}
		}
		return res.toString();
	}
}
