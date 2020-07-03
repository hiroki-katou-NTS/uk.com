package nts.uk.ctx.at.shared.app.find.worktime.predset.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
@AllArgsConstructor
@Data
public class TimeZoneWithWorkNoDto {
	/**
	 * 勤務NO
	 */
	private int workNo;
	
	/**
	 * 時間帯
	 */
	private TimeZone_NewDto timeZone;
	
	public static TimeZoneWithWorkNoDto fromDomain(TimeZoneWithWorkNo timeZoneWithWorkNo) {
		return new TimeZoneWithWorkNoDto(timeZoneWithWorkNo.getWorkNo().v(), TimeZone_NewDto.fromDomain(timeZoneWithWorkNo.getTimeZone()));
	}
}
