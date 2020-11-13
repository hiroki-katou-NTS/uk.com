package nts.uk.ctx.at.function.dom.processexecution.personalschedule;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.shr.com.time.calendar.MonthDay;

import java.util.Optional;

/**
 * The class Personal schedule creation period.<br>
 * Domain 個人スケジュール作成期間
 *
 * @author nws-minhnb
 */
@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PersonalScheduleCreationPeriod extends DomainObject {

	/**
	 * The Creation period.<br>
	 * 作成期間
	 */
	private Optional<CreationPeriod> creationPeriod;

	/**
	 * The Target date.<br>
	 * 対象日
	 */
	private Optional<TargetDate> targetDate;

	/**
	 * The Target month.<br>
	 * 対象月
	 */
	private TargetMonth targetMonth;

	/**
	 * The Designated year.<br>
	 * 指定年
	 */
	private Optional<CreateScheduleYear> designatedYear;

	/**
	 * The Start month day.<br>
	 * 指定開始月日
	 */
	private Optional<MonthDay> startMonthDay;

	/**
	 * The End month day.<br>
	 * 指定終了月日
	 */
	private Optional<MonthDay> endMonthDay;

	/**
	 * Instantiates a new <code>PersonalScheduleCreationPeriod</code>.
	 *
	 * @param creationPeriod the creation period
	 * @param targetDate     the target date
	 * @param targetMonth    the target month
	 * @param designatedYear the designated year
	 * @param startMonthDay  the start month day
	 * @param endMonthDay    the end month day
	 */
	public PersonalScheduleCreationPeriod(Integer creationPeriod, Integer targetDate, int targetMonth,
										  Integer designatedYear, Integer startMonthDay, Integer endMonthDay) {
		super();
		this.creationPeriod = Optional.ofNullable(creationPeriod).map(CreationPeriod::new);
		this.targetDate = Optional.ofNullable(targetDate).map(TargetDate::new);
		this.targetMonth = EnumAdaptor.valueOf(targetMonth, TargetMonth.class);
		this.designatedYear = Optional.ofNullable(designatedYear).map(value -> EnumAdaptor.valueOf(value, CreateScheduleYear.class));
		this.startMonthDay = Optional.ofNullable(this.convertIntegerToMonthDay(startMonthDay));
		this.endMonthDay = Optional.ofNullable(this.convertIntegerToMonthDay(endMonthDay));
	}

	/**
	 * Gets <code>Integer</code> value of start month day.
	 *
	 * @return the <code>Integer</code> value of {@link #startMonthDay}
	 * @throws RuntimeException the runtime exception
	 */
	public Integer getStartMonthDayIntVal() throws RuntimeException {
		return this.startMonthDay.map(this::convertMonthDayToInteger).orElse(null);
	}

	/**
	 * Gets <code>Integer</code> value of end month day.
	 *
	 * @return the <code>Integer</code> value of {@link #endMonthDay}
	 * @throws RuntimeException the runtime exception
	 */
	public Integer getEndMonthDayIntVal() throws RuntimeException {
		return this.endMonthDay.map(this::convertMonthDayToInteger).orElse(null);
	}

	/**
	 * Convert <code>Integer</code> to <code>MonthDay</code>.
	 *
	 * @param intValue the <code>Integer</code> value
	 * @return the month day
	 * @throws RuntimeException the runtime exception
	 */
	private MonthDay convertIntegerToMonthDay(Integer intValue) throws RuntimeException {
		if (intValue == null) {
			return null;
		}
		MonthDay monthDay = new MonthDay(intValue / 100, intValue % 100);
		monthDay.validate();
		return monthDay;
	}

	/**
	 * Convert <code>MonthDay</code> to <code>Integer</code>.
	 *
	 * @param monthDay the month day
	 * @return the <code>Integer</code> value
	 * @throws RuntimeException the runtime exception
	 */
	private Integer convertMonthDayToInteger(MonthDay monthDay) throws RuntimeException {
		if (monthDay == null) {
			return null;
		}
		monthDay.validate();
		return (monthDay.getMonth() * 100) + monthDay.getDay();
	}

}
