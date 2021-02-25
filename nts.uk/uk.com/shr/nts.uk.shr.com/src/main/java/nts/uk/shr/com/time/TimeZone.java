package nts.uk.shr.com.time;

import lombok.Getter;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".Common.時間.時間帯
 * @author Doan Duy Hung
 *
 */
@Getter
public class TimeZone {
	
	/**
	 * 開始時刻
	 */
	private TimeWithDayAttr startTime;
	
	/**
	 * 終了時刻
	 */
	private TimeWithDayAttr endTime;
	
	public TimeZone(Integer startTime, Integer endTime) {
		if (startTime != null) {
			this.startTime = new TimeWithDayAttr(startTime);			
		}
		if (endTime != null) {
			this.endTime = new TimeWithDayAttr(endTime);			
		}
	}
}
