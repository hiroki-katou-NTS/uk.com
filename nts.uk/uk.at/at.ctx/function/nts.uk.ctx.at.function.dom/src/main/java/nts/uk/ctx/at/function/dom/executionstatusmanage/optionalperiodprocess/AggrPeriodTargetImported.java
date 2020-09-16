package nts.uk.ctx.at.function.dom.executionstatusmanage.optionalperiodprocess;

import lombok.Builder;
import lombok.Data;
import nts.uk.ctx.at.function.dom.executionstatusmanage.optionalperiodprocess.periodtarget.State;

@Data
@Builder
public class AggrPeriodTargetImported {
	/** 任意期間集計実行ログID*/
	private String aggrId;
	
	/** 社員ID*/
	private String employeeId;
	
	/** 状態*/
	private State state;

	/**
	 * 
	 * @param anyPeriodAggrLogId
	 * @param memberId
	 * @param state
	 */
}
