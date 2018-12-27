package nts.uk.ctx.at.function.app.find.dailyperformanceformat.dto;

import lombok.Value;

@Value
public class DailyPerformanceCodeDto {

	private String dailyPerformanceFormatCode;
	
	private String dailyPerformanceFormatName;
	
	private boolean isSetFormatToDefault;
}
