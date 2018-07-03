package nts.uk.ctx.at.record.app.find.resultsperiod.optionalaggregationperiod;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodExcution;
@NoArgsConstructor
@AllArgsConstructor
@Data
/**
 * 
 * @author phongtq
 *
 */
public class AggrPeriodExcutionDto {
	private String companyId;

	private String executionEmpId;

	private String aggrFrameCode;

	private String aggrId;
	private GeneralDateTime startDateTime;

	private GeneralDateTime endDateTime;

	private int executionAtr;

	private int executionStatus;

	private int presenceOfError;

	public static AggrPeriodExcutionDto fromDomain(AggrPeriodExcution domain){
		return new AggrPeriodExcutionDto(
				domain.getCompanyId(),
				domain.getExecutionEmpId(),
				domain.getAggrFrameCode().v(),
				domain.getAggrId(),
				domain.getStartDateTime(),
				domain.getEndDateTime(),
				domain.getExecutionAtr().value,
				domain.getExecutionStatus().get().value,
				domain.getPresenceOfError().value
				);
	}
}
