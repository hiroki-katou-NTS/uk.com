package nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto;

import java.util.List;

import lombok.Value;

@Value
public class WorkTypeDetailDto {
	
	private List<WorkTypeFormatDailyDto> workTypeFormatDailyDto;
	
	private List<WorkTypeFormatMonthlyDto> workTypeFormatMonthlyDto;

}
