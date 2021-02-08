package nts.uk.ctx.at.request.app.command.application.overtime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.app.find.worktime.predset.dto.TimeZone_NewDto;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;

@AllArgsConstructor
@NoArgsConstructor
public class TimeZoneWithWorkNoCommand {
	/**
	 * 勤務NO
	 */
	public int workNo;
	
	/**
	 * 時間帯
	 */
	public TimeZone_NewDto timeZone;
	
	public TimeZoneWithWorkNo toDomain() {
		TimeZoneWithWorkNo timeZoneWithWorkNo = new TimeZoneWithWorkNo(workNo, timeZone.getStartTime(), timeZone.getEndTime());
		return timeZoneWithWorkNo;
	}
}
