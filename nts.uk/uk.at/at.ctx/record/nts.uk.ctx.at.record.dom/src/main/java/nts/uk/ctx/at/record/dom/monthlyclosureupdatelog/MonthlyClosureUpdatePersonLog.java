package nts.uk.ctx.at.record.dom.monthlyclosureupdatelog;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 
 * @author HungTT - 月締め更新対象者ログ
 *
 */

@Getter
public class MonthlyClosureUpdatePersonLog extends AggregateRoot {

	// 社員ID
	private String employeeId;

	// 月締め更新ログID
	private String monthlyClosureUpdateLogId;

	// 実行結果
	private MonthlyClosurePersonExecutionResult executionResult;

	// 実行状況
	private MonthlyClosurePersonExecutionStatus executionStatus;

	public MonthlyClosureUpdatePersonLog(String employeeId, String monthlyClosureUpdateLogId, int executionResult,
			int executionStatus) {
		super();
		this.employeeId = employeeId;
		this.monthlyClosureUpdateLogId = monthlyClosureUpdateLogId;
		this.executionResult = EnumAdaptor.valueOf(executionResult, MonthlyClosurePersonExecutionResult.class);
		this.executionStatus = EnumAdaptor.valueOf(executionStatus, MonthlyClosurePersonExecutionStatus.class);
	}

	public MonthlyClosureUpdatePersonLog(String employeeId, String monthlyClosureUpdateLogId,
			MonthlyClosurePersonExecutionResult executionResult, MonthlyClosurePersonExecutionStatus executionStatus) {
		super();
		this.employeeId = employeeId;
		this.monthlyClosureUpdateLogId = monthlyClosureUpdateLogId;
		this.executionResult = executionResult;
		this.executionStatus = executionStatus;
	}

	public void updateResult(MonthlyClosurePersonExecutionResult result) {
		this.executionResult = result;
	}

	public void updateStatus(MonthlyClosurePersonExecutionStatus status) {
		this.executionStatus = status;
	}

}
