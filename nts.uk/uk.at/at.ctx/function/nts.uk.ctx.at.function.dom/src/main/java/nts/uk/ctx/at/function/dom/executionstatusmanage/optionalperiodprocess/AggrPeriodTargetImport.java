package nts.uk.ctx.at.function.dom.executionstatusmanage.optionalperiodprocess;

import lombok.Builder;
import lombok.Data;

/**
 * The Class AggrPeriodTargetImport.
 * 	任意期間集計対象者
 */
@Data
@Builder
public class AggrPeriodTargetImport {
	/** 任意期間集計実行ログID */
	private String aggrId;

	/** 社員ID */
	private String employeeId;

	/** 状態 */
	private int state;
}
