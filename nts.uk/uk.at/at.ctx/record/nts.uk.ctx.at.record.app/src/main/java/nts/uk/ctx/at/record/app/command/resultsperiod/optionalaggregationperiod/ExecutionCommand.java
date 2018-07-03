package nts.uk.ctx.at.record.app.command.resultsperiod.optionalaggregationperiod;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodExcution;

@AllArgsConstructor
@Data
public class ExecutionCommand {

	private String companyId;

	private String aggrFrameCode;

	private GeneralDateTime startDateTime;

	private GeneralDateTime endDateTime;

	private int executionAtr;

	private int executionStatus;

	private int presenceOfError;

	public AggrPeriodExcution toDomain(String companyId, String executionEmpId, String aggrId, GeneralDateTime startDateTime, GeneralDateTime endDateTime) {
		return AggrPeriodExcution.createFromJavaType(companyId, executionEmpId, this.aggrFrameCode, aggrId,
				startDateTime, endDateTime, this.executionAtr, this.executionStatus, this.presenceOfError);

	}
}
