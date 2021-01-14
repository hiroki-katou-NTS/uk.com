package nts.uk.query.app.ccg005.query.work.information.work.schedule.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BreakTimeOfDailyAttdDto {
	//時間帯
	private List<BreakTimeSheetDto> breakTimeSheets;
}
