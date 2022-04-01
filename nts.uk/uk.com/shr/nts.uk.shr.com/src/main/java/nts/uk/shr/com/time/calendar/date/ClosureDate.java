package nts.uk.shr.com.time.calendar.date;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.val;
import nts.arc.layer.dom.objecttype.DomainObject;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.shr.com.time.calendar.Day;

/**
 * 日付
 */
@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class ClosureDate implements DomainObject {

	/** 日 */
	private final Day closureDay;

	/** 末日とする */
	private final Boolean lastDayOfMonth;

	/**
	 * Instantiates a new closure date.
	 *
	 * @param closureDay
	 *            the closure day
	 * @param lastDayOfMonth
	 *            the last day of month
	 */
	public ClosureDate(Integer closureDay, Boolean lastDayOfMonth) {
		this.closureDay = new Day(lastDayOfMonth ? 1 : closureDay);
		this.lastDayOfMonth = lastDayOfMonth;
	}

	/**
	 * 指定年月の期間を求める
	 * @param yearMonth 年月
	 * @return
	 */
	public DatePeriod periodOf(YearMonth yearMonth) {
		if (lastDayOfMonth) {
			return new DatePeriod(
					GeneralDate.ymd(yearMonth.year(), yearMonth.month(), 1),
					GeneralDate.ymd(yearMonth.year(), yearMonth.month(), yearMonth.lastDateInMonth()));
		}

		val start = GeneralDate.ymd(yearMonth.year(), yearMonth.month(), closureDay.v());
		val end = start.addMonths(1).addDays(-1);
		return new DatePeriod(start, end);
	}
}
