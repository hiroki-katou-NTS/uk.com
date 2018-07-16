package nts.uk.ctx.at.record.app.command.resultsperiod.optionalaggregationperiod;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodTarget;

@AllArgsConstructor
@Data
public class TargetCommand {

	private String aggrId;

	private List<String> employeeId;

	private int state;
	
	public List<AggrPeriodTarget> toDomain(String aggrId) {
		return this.employeeId.stream().map(item -> AggrPeriodTarget.createFromJavaType(aggrId,item,this.state)).collect(Collectors.toList());
	}

}
