package nts.uk.ctx.at.record.app.find.resultsperiod.optionalaggregationperiod;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
/**
 * 
 * @author phongtq
 *
 */
public class OptionalAggrDto {
	private boolean mode;

	private AnyAggrPeriodDto anyAggrPeriodDto;

	private AggrPeriodExcutionDto aggrPeriodExcutionDto;
	
	private PeriodTargetDto aggrPeriodTargetDto;

}