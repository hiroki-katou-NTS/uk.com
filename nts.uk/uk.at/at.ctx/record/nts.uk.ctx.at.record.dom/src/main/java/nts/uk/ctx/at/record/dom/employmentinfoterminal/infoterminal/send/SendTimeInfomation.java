package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.send;

import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainValue;

/**
 * @author ThanhNX
 *
 *         時刻情報
 */
@Value
public class SendTimeInfomation implements DomainValue {

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

	/**
	 * week
	 */
	private final Integer week;

	public SendTimeInfomation(Integer year, Integer month, Integer day, Integer hour, Integer minute, Integer second,
			Integer week) {
		super();
		this.year = year;
		this.month = month;
		this.day = day;
		this.hour = hour;
		this.minute = minute;
		this.second = second;
		this.week = week;
	}

}
