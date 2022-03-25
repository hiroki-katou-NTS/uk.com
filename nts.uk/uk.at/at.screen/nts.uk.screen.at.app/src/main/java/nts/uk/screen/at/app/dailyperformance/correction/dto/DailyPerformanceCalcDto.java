package nts.uk.screen.at.app.dailyperformance.correction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DailyPerformanceCalcDto {
	
	DailyPerformanceCalculationDto dailyPerformanceCalculationDto;
	
	DataSessionDto dataSessionDto;
}
