package nts.uk.ctx.at.request.app.find.application.overtime.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZone;

@AllArgsConstructor
@NoArgsConstructor
public class TimeZoneDto {
	// 開始
	public Integer start;

	/** The end. */
	// 終了
	public Integer end;
	
	public static TimeZoneDto fromDomain(TimeZone timeZone) {
		return new TimeZoneDto(timeZone.getStart().v(), timeZone.getEnd().v());
	}
}
