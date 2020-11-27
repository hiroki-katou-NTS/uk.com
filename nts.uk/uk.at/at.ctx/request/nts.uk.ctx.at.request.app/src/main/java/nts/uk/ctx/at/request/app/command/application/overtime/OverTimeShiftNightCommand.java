package nts.uk.ctx.at.request.app.command.application.overtime;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.overtime.OverTimeShiftNight;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

public class OverTimeShiftNightCommand {
	// 休出深夜時間
	public List<HolidayMidNightTimeCommand> midNightHolidayTimes;
	// 合計外深夜時間
	public Integer midNightOutSide;
	// 残業深夜時間
	public Integer overTimeMidNight;
	
	public OverTimeShiftNight toDomain() {
		
		return new OverTimeShiftNight(
				CollectionUtil.isEmpty(midNightHolidayTimes) ? 
							Collections.emptyList() : 
							midNightHolidayTimes.stream()
												.map(x -> x.toDomain())
												.collect(Collectors.toList()),
				midNightOutSide == null ? null : new AttendanceTime(midNightOutSide),
				overTimeMidNight == null ? null : new AttendanceTime(overTimeMidNight));
	}
}
