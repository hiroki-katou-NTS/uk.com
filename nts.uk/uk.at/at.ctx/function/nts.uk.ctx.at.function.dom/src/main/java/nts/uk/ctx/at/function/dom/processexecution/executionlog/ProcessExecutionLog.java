package nts.uk.ctx.at.function.dom.processexecution.executionlog;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.function.dom.processexecution.ExecutionCode;

/**
 * 更新処理自動実行ログ
 */
@Getter
@Setter
@AllArgsConstructor
public class ProcessExecutionLog extends AggregateRoot {
	/* コード */
	private ExecutionCode execItemCd;
	
	/* 会社ID */
	private String companyId;
	
	/* 全体のエラー詳細 */
	private OverallErrorDetail overallError;
	
	/* 全体の終了状態 */
	private EndStatus overallStatus;
	
	/* 前回実行日時 */
	private GeneralDateTime lastExecDateTime;
	
	/* 各処理の期間 */
	private EachProcessPeriod eachProcPeriod;
	
	/* 各処理の終了状態 */
	private List<ExecutionTaskLog> taskLogList;
	
	/* 実行ID */
	private String execId;
	
	/* 現在の実行状態 */
	private CurrentExecutionStatus currentStatus;
	
	/* 前回実行日時（即時実行含めない） */
	private GeneralDateTime lastExecDateTimeEx;

	public ProcessExecutionLog(ExecutionCode execItemCd, String companyId, EndStatus overallStatus,
			String execId, CurrentExecutionStatus currentStatus) {
		super();
		this.execItemCd = execItemCd;
		this.companyId = companyId;
		this.overallStatus = overallStatus;
		this.execId = execId;
		this.currentStatus = currentStatus;
		this.overallError = null;
		this.lastExecDateTime = null;
		this.eachProcPeriod = null;
		this.taskLogList = new ArrayList<>();
		this.lastExecDateTimeEx = null;
	}
	
	public void initTaskLogList() {
		this.taskLogList.add(new ExecutionTaskLog(ProcessExecutionTask.SCH_CREATION , EndStatus.NOT_IMPLEMENT));
		this.taskLogList.add(new ExecutionTaskLog(ProcessExecutionTask.DAILY_CREATION , EndStatus.NOT_IMPLEMENT));
		this.taskLogList.add(new ExecutionTaskLog(ProcessExecutionTask.DAILY_CALCULATION , EndStatus.NOT_IMPLEMENT));
		this.taskLogList.add(new ExecutionTaskLog(ProcessExecutionTask.RFL_APR_RESULT , EndStatus.NOT_IMPLEMENT));
		this.taskLogList.add(new ExecutionTaskLog(ProcessExecutionTask.MONTHLY_AGGR , EndStatus.NOT_IMPLEMENT));
		this.taskLogList.add(new ExecutionTaskLog(ProcessExecutionTask.INDV_ALARM , EndStatus.NOT_IMPLEMENT));
		this.taskLogList.add(new ExecutionTaskLog(ProcessExecutionTask.WKP_ALARM , EndStatus.NOT_IMPLEMENT));
	}
}
