package nts.uk.screen.at.app.dailyperformance.correction.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.screen.at.app.dailyperformance.correction.month.asynctask.ParamCommonAsync;

@Getter
@Setter
@NoArgsConstructor
public class InitParamOutput {
	
	private DailyPerformanceCorrectionDto dailyPerformanceCorrectionDto;
	
	private ParamCommonAsync paramCommonAsync;

	public InitParamOutput(DailyPerformanceCorrectionDto dailyPerformanceCorrectionDto,
			ParamCommonAsync paramCommonAsync) {
		super();
		this.dailyPerformanceCorrectionDto = dailyPerformanceCorrectionDto;
		this.paramCommonAsync = paramCommonAsync;
	}
	
	
	
}
