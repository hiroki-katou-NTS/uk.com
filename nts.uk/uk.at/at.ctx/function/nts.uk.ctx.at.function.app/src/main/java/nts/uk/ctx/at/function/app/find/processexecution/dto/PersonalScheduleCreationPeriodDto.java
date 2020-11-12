package nts.uk.ctx.at.function.app.find.processexecution.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.function.dom.processexecution.personalschedule.PersonalScheduleCreationPeriod;
import nts.uk.shr.com.time.calendar.MonthDay;

/**
 * The class Personal schedule creation period dto.<br>
 * Dto 個人スケジュール作成期間
 *
 * @author nws-minhnb
 */
@Data
@AllArgsConstructor
public class PersonalScheduleCreationPeriodDto {

	/**
	 * 作成期間
	 */
	private int creationPeriod;

	/**
	 * 対象日
	 */
	private int targetDate;

	/**
	 * 対象月
	 */
	private int targetMonth;

	/**
	 * 指定年
	 */
	private Integer designatedYear;

	/**
	 * 指定開始月日
	 */
	private Integer startMonthDay;

	/**
	 * 指定終了月日
	 */
	private Integer endMonthDay;

	/**
	 * No args constructor.
	 */
	private PersonalScheduleCreationPeriodDto() {
	}

	/**
	 * Create from domain.
	 *
	 * @param domain the domain
	 * @return the Personal schedule creation dto
	 */
	public static PersonalScheduleCreationPeriodDto createFromDomain(PersonalScheduleCreationPeriod domain) {
		if (domain == null) {
			return null;
		}
		PersonalScheduleCreationPeriodDto dto = new PersonalScheduleCreationPeriodDto();
		dto.creationPeriod = domain.getCreationPeriod().v();
		dto.targetDate = domain.getTargetDate().v();
		dto.targetMonth = domain.getTargetMonth().value;
		dto.designatedYear = domain.getDesignatedYear()
								   .map(designatedYear -> designatedYear.value)
								   .orElse(null);
		dto.startMonthDay = domain.getStartMonthDay()
								  .map(PersonalScheduleCreationPeriodDto::monthDayToIntegerValue)
								  .orElse(null);
		dto.endMonthDay = domain.getEndMonthDay()
								.map(PersonalScheduleCreationPeriodDto::monthDayToIntegerValue)
								.orElse(null);
		return dto;
	}

	/**
	 * Month day to integer value.
	 *
	 * @param monthDay the month day
	 * @return the integer
	 */
	private static Integer monthDayToIntegerValue(MonthDay monthDay) {
		if (monthDay == null) {
			return null;
		}
		return (monthDay.getMonth() * 100) + monthDay.getDay();
	}

}
