package nts.uk.ctx.at.record.app.find.resultsperiod.optionalaggregationperiod;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodTarget;
import nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod.OptionalAggrPeriod;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PeriodTargetDto {
	private String aggrId;

	private List<String> employeeId;

	private int state;


}
