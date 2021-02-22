package nts.uk.ctx.at.record.app.find.resultsperiod.optionalaggregationperiod;

import java.util.List;

import lombok.Value;
import nts.uk.ctx.at.record.dom.adapter.employee.EmployeeDto;

@Value
public class AggrPeriodDto {

	/** The aggr period excution dto. */
	private AggrPeriodExcutionDto aggrPeriodExcutionDto;
	
	/** The aggr period dto. */
	private AnyAggrPeriodDto aggrPeriodDto;
	
	/** The period target dto. */
	private List<AggrPeriodTargetDto> aggrPeriodTargetDto;
	
	/** The error infos. */
	private List<AggrPeriodErrorInfoDto> errorInfos;
	
	private List<EmployeeDto> employeeDto;
}
