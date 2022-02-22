package nts.uk.screen.at.app.dailyperformance.correction.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.screen.at.app.dailyperformance.correction.dto.cache.DPCorrectionStateParam;
import nts.uk.screen.at.app.dailyperformance.correction.month.asynctask.ParamCommonAsync;

@Getter
@Setter
@NoArgsConstructor
public class InitParamOutput {
	
	private DailyPerformanceCorrectionDto dailyPerformanceCorrectionDto;
	
	private ParamCommonAsync paramCommonAsync;
	
	private DPCorrectionStateParam dpStateParam;

	public InitParamOutput(DailyPerformanceCorrectionDto dailyPerformanceCorrectionDto,
			ParamCommonAsync paramCommonAsync, DPCorrectionStateParam dpStateParam) {
		super();
		this.dailyPerformanceCorrectionDto = dailyPerformanceCorrectionDto;
		this.paramCommonAsync = paramCommonAsync;
		this.dpStateParam = dpStateParam;
	}

	
	
	
}
