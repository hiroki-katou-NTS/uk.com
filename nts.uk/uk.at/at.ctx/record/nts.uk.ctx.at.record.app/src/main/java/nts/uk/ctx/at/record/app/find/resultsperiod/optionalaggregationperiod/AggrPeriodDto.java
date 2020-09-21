package nts.uk.ctx.at.record.app.find.resultsperiod.optionalaggregationperiod;

import java.util.List;

import lombok.Value;

@Value
public class AggrPeriodDto {

	/** The aggr period excution dto. */
	private AggrPeriodExcutionDto aggrPeriodExcutionDto;
	
	/** The aggr period dto. */
	private OptionalAggrPeriodDto aggrPeriodDto;
	
	/** The period target dto. */
	private List<AggrPeriodTargetDto> periodTargetDto;
	
	/** The error infos. */
	private List<AggrPeriodErrorInfoDto> errorInfos;
}
