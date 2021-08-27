package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.send;

import org.apache.commons.lang3.StringUtils;

import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainValue;
import nts.arc.time.GeneralDateTime;

/**
 * @author thanh_nx
 *
 *         UKモードへの切替日時送信
 */
@Value
public class DateTimeSwitchUKMode implements DomainValue {

	/**
	 * Year
	 */
	private final String year;

	/**
	 * month
	 */
	private final String month;

	/**
	 * day
	 */
	private final String day;

	/**
	 * hour
	 */
	private final String hour;

	/**
	 * minute
	 */
	private final String minute;

	/**
	 * second
	 */
	private final String second;
	
	/**
	 * week
	 */
	private final String week;

	public DateTimeSwitchUKMode(String year, String month, String day, String hour, String minute,
			String second, String week) {
		super();
		this.year = year;
		this.month = month;
		this.day = day;
		this.hour = hour;
		this.minute = minute;
		this.second = second;
		this.week = week;
	}

	public static DateTimeSwitchUKMode create(GeneralDateTime time) {
		return new DateTimeSwitchUKMode(hexPadding(time.year(), 4), hexPadding(time.month(), 2),
				hexPadding(time.day(), 2), hexPadding(time.hours(), 2), hexPadding(time.minutes(), 2),
				hexPadding(time.seconds(), 2), hexPadding(time.dayOfWeek()-1, 2));
	}

    //[S-1] hexパディング
	private static String hexPadding(int data, int length) {
		return StringUtils.leftPad(Integer.toHexString(data), length, "0").toUpperCase();
	}
}
