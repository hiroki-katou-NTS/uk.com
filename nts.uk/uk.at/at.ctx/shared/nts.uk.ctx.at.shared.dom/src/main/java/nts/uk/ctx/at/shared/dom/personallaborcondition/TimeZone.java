/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.personallaborcondition;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class TimeZone.
 */
// 時間帯
@Getter
public class TimeZone extends DomainObject{
	
	/** The use atr. */
	// 使用区分
	private UseAtr useAtr;
	
	/** The times. */
	// 回数
	private int times;
	
	/** The start. */
	// 開始
	private TimeWithDayAttr start;
	
	/** The end. */
	// 終了
	private TimeWithDayAttr end;

	/**
	 * Instantiates a new time zone.
	 *
	 * @param useAtr the use atr
	 */
	public TimeZone(UseAtr useAtr) {
		this.useAtr = useAtr;
	}
	
	/**
	 * Default time zone.
	 *
	 * @param times the times
	 * @param start the start
	 * @param end the end
	 */
	public void defaultTimeZone(int times, int start, int end) {
		this.times = times;
		this.start = new TimeWithDayAttr(start);
		this.end = new TimeWithDayAttr(end);
	}
	
	
}
