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
	private Integer startTime;
	
	/**
	 * 終了時刻
	 */
	private Integer endTime;
	
	public static TimeZone_NewDto fromDomain(TimeZone timeZone) {
		return new TimeZone_NewDto(timeZone.getStartTime().v(), timeZone.getEndTime().v());
	}
	
	public TimeZone toDomain() {
		return new TimeZone(startTime, endTime);
	}
}
