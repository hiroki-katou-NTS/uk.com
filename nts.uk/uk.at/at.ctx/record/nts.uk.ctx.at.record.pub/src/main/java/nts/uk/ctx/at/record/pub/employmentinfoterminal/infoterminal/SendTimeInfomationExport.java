package nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal;

import lombok.Value;

/**
 * @author ThanhNX
 *
 *         時刻情報Export
 */
@Value
public class SendTimeInfomationExport {

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

	public SendTimeInfomationExport(String year, String month, String day, String hour, String minute,
			String second, String week) {
		this.year = year;
		this.month = month;
		this.day = day;
		this.hour = hour;
		this.minute = minute;
		this.second = second;
		this.week = week;
	}

}
