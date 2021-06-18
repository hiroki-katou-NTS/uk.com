package nts.uk.ctx.at.request.app.find.application.overtime;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.overtime.OverTimeShiftNight;

@AllArgsConstructor
@NoArgsConstructor
public class OverTimeShiftNightDto {
	// 休出深夜時間
	public List<HolidayMidNightTimeDto> midNightHolidayTimes;
	// 合計外深夜時間
	public Integer midNightOutSide;
	// 残業深夜時間
	public Integer overTimeMidNight;
	
	public static OverTimeShiftNightDto fromDomain(OverTimeShiftNight overTimeShiftNight) {
		if (overTimeShiftNight == null) return null;
		return new OverTimeShiftNightDto(
				overTimeShiftNight.getMidNightHolidayTimes()
					.stream()
					.map(x -> HolidayMidNightTimeDto.fromDomain(x))
					.collect(Collectors.toList()),
				overTimeShiftNight.getMidNightOutSide() == null ? null : overTimeShiftNight.getMidNightOutSide().v(),
				overTimeShiftNight.getOverTimeMidNight() == null ? null : overTimeShiftNight.getOverTimeMidNight().v());
	}
}
