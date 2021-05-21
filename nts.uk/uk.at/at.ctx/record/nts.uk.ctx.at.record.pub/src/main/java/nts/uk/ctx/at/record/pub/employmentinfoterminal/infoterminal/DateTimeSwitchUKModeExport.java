package nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal;

import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainValue;
import nts.arc.time.GeneralDateTime;

/**
 * @author thanh_nx
 *
 *         UKモードへの切替日時送信Export
 */
@Value
public class DateTimeSwitchUKModeExport implements DomainValue {

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

	public DateTimeSwitchUKModeExport(Integer year, Integer month, Integer day, Integer hour, Integer minute,
			Integer second) {
		super();
		this.year = year;
		this.month = month;
		this.day = day;
		this.hour = hour;
		this.minute = minute;
		this.second = second;
	}

	public static DateTimeSwitchUKModeExport create(GeneralDateTime time) {
		return new DateTimeSwitchUKModeExport(time.year() % 100, time.month(), time.day(), time.hours(), time.minutes(),
				time.seconds());
	}
}
