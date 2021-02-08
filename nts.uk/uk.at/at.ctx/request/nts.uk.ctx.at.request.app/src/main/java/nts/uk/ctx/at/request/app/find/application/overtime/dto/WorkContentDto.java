package nts.uk.ctx.at.request.app.find.application.overtime.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.overtime.service.WorkContent;

@AllArgsConstructor
@NoArgsConstructor
public class WorkContentDto {
	// 勤務種類コード
	public String workTypeCode;
	// 就業時間帯コード
	public String workTimeCode;
	// 時間帯 NO = 1 and NO = 2
	public List<TimeZoneDto> timeZones;
	// 休憩時間帯 休憩枠NO = 1 ~ 10
	public List<BreakTimeSheetDto> breakTimes;
	
	public static WorkContentDto fromDomain(WorkContent workContent) {
		
		return new WorkContentDto(
				workContent.getWorkTypeCode().orElse(null),
				workContent.getWorkTimeCode().orElse(null),
				workContent.getTimeZones()
					.stream()
					.map(x -> TimeZoneDto.fromDomain(x))
					.collect(Collectors.toList()),
				workContent.getBreakTimes()
					.stream()
					.map(x -> BreakTimeSheetDto.fromDomain(x))
					.collect(Collectors.toList()));
	}
}
