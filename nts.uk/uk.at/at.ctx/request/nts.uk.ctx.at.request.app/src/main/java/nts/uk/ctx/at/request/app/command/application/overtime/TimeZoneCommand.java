package nts.uk.ctx.at.request.app.command.application.overtime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZone;
import nts.uk.shr.com.time.TimeWithDayAttr;

@AllArgsConstructor
@NoArgsConstructor
public class TimeZoneCommand {
	// 開始
	public Integer start;

	// 終了
	public Integer end;
	
	public TimeZone toDomain() {
		return new TimeZone(
				new TimeWithDayAttr(start),
				new TimeWithDayAttr(end));
	}
}
