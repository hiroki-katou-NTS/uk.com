package nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal;

import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainValue;

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

	public DateTimeSwitchUKModeExport(String year, String month, String day, String hour, String minute,
			String second) {
		super();
		this.year = year;
		this.month = month;
		this.day = day;
		this.hour = hour;
		this.minute = minute;
		this.second = second;
	}
}
