package nts.uk.ctx.at.function.dom.processexecution.tasksetting.detail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 *	繰り返す月
 */
@Getter
@AllArgsConstructor
public class RepeatMonthSelect extends DomainObject {
	/* 1月 */
	private NotUseAtr january;
	
	/* 2月 */
	private NotUseAtr february;
	
	/* 3月 */
	private NotUseAtr march;
	
	/* 4月 */
	private NotUseAtr april;
	
	/* 5月 */
	private NotUseAtr may;
	
	/* 6月 */
	private NotUseAtr june;
	
	/* 7月 */
	private NotUseAtr july;
	
	/* 8月 */
	private NotUseAtr august;
	
	/* 9月 */
	private NotUseAtr september;
	
	/* 10月 */
	private NotUseAtr october;
	
	/* 11月 */
	private NotUseAtr november;
	
	/* 12月 */
	private NotUseAtr december;
	
	@Getter(value = AccessLevel.NONE)
	private Map<Integer, Boolean> values;
	
	public RepeatMonthSelect(boolean january, boolean february, boolean march, boolean april, boolean may, boolean june,
			boolean july, boolean august, boolean september, boolean october, boolean november, boolean december) {
		this.january = january ? NotUseAtr.USE : NotUseAtr.NOT_USE;
		this.february = february ? NotUseAtr.USE : NotUseAtr.NOT_USE;
		this.march = march ? NotUseAtr.USE : NotUseAtr.NOT_USE;
		this.april = april ? NotUseAtr.USE : NotUseAtr.NOT_USE;
		this.may = may ? NotUseAtr.USE : NotUseAtr.NOT_USE;
		this.june = june ? NotUseAtr.USE : NotUseAtr.NOT_USE;
		this.july = july ? NotUseAtr.USE : NotUseAtr.NOT_USE;
		this.august = august ? NotUseAtr.USE : NotUseAtr.NOT_USE;
		this.september = september ? NotUseAtr.USE : NotUseAtr.NOT_USE;
		this.october = october ? NotUseAtr.USE : NotUseAtr.NOT_USE;
		this.november = november ? NotUseAtr.USE : NotUseAtr.NOT_USE;
		this.december = december ? NotUseAtr.USE : NotUseAtr.NOT_USE;
	}

	public boolean isCheckedAtLeastOne() {
		if (values == null) {
			values = this.generateValuesMap();
		}
		return values.values().isEmpty();
	}
	
	public List<Integer> getMonthsAfterEqualsStartMonth(Integer startMonth) {
		if (values == null) {
			values = this.generateValuesMap();
		}
		return values.keySet().stream().filter(key -> key >= startMonth).collect(Collectors.toList());
	}
	
	public static boolean isTrue(NotUseAtr atr) {
		return atr.equals(NotUseAtr.USE);
	}
	
	private Map<Integer, Boolean> generateValuesMap() {
		Map<Integer, Boolean> values = new HashMap<>();
		values.put(1, isTrue(january));
		values.put(2, isTrue(february));
		values.put(3, isTrue(march));
		values.put(4, isTrue(april));
		values.put(5, isTrue(may));
		values.put(6, isTrue(june));
		values.put(7, isTrue(july));
		values.put(8, isTrue(august));
		values.put(9, isTrue(september));
		values.put(10, isTrue(october));
		values.put(11, isTrue(november));
		values.put(12, isTrue(december));
		return values.entrySet().stream()
				.filter(Entry::getValue)
				.collect(Collectors.toMap(Entry::getKey, Entry::getValue));
	}
}
