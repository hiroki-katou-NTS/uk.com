package nts.uk.ctx.at.request.app.command.application.overtime;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.uk.ctx.at.request.dom.application.overtime.service.WorkContent;

public class WorkContentCommand {
	// 勤務種類コード
	public String workTypeCode;
	// 就業時間帯コード
	public String workTimeCode;
	// 時間帯 NO = 1 and NO = 2
	public List<TimeZoneCommand> timeZones;
	// 休憩時間帯 休憩枠NO = 1 ~ 10
	public List<BreakTimeSheetCommand> breakTimes;
	
	public WorkContent toDomain() {
		
		
		return new WorkContent(
				Optional.ofNullable(workTypeCode),
				Optional.ofNullable(workTimeCode),
				timeZones.isEmpty() ? 
						Collections.emptyList() : 
						timeZones
							.stream()
							.map(x -> x.toDomain())
							.collect(Collectors.toList()),
				breakTimes.isEmpty() ? 
						Collections.emptyList() : 
						breakTimes
							.stream()
							.map(x -> x.toDomain())
							.collect(Collectors.toList()));
	}
}
