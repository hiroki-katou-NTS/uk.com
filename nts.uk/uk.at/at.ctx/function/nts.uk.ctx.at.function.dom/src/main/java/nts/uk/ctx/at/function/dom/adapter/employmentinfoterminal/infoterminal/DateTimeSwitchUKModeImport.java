package nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal;

import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainValue;
import nts.arc.time.GeneralDateTime;

/**
 * @author thanh_nx
 *
 *         UKモードへの切替日時送信Import
 */
@Value
public class DateTimeSwitchUKModeImport implements DomainValue {

	/**
	 * Year
	 */
	private final Integer year;

	/**
	 * month
	 */
	private final Integer month;

	/**
	 * day
	 */
	private final Integer day;

	/**
	 * hour
	 */
	private final Integer hour;

	/**
	 * minute
	 */
	private final Integer minute;

	/**
	 * second
	 */
	private final Integer second;

	public DateTimeSwitchUKModeImport(Integer year, Integer month, Integer day, Integer hour, Integer minute,
			Integer second) {
		super();
		this.year = year;
		this.month = month;
		this.day = day;
		this.hour = hour;
		this.minute = minute;
		this.second = second;
	}

	public static DateTimeSwitchUKModeImport create(GeneralDateTime time) {
		return new DateTimeSwitchUKModeImport(time.year() % 100, time.month(), time.day(), time.hours(), time.minutes(),
				time.seconds());
	}
}
