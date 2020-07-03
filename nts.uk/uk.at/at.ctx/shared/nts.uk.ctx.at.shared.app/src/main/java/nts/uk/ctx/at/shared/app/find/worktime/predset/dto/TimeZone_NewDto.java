package nts.uk.ctx.at.shared.app.find.worktime.predset.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.shr.com.time.TimeZone;
@Data
@AllArgsConstructor
public class TimeZone_NewDto {
	/**
	 * 開始時刻
	 */
	private int startTime;
	
	/**
	 * 終了時刻
	 */
	private int endTime;
	
	public static TimeZone_NewDto fromDomain(TimeZone timeZone) {
		return new TimeZone_NewDto(timeZone.getStartTime().v(), timeZone.getEndTime().v());
	}
}
