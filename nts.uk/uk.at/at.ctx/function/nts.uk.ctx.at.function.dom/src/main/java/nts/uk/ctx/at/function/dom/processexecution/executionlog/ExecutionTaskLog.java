package nts.uk.ctx.at.function.dom.processexecution.executionlog;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 更新処理自動実行タスクログ
 */
@Getter
@Setter
public class ExecutionTaskLog {
	/* 更新処理 */
	private ProcessExecutionTask procExecTask;
	
	/* 終了状態 */
	private Optional<EndStatus> status;
	
	
	private String execId;

	public ExecutionTaskLog(ProcessExecutionTask procExecTask, Optional<EndStatus> status) {
		super();
		this.procExecTask = procExecTask;
		this.status = status;
	}

	public ExecutionTaskLog(ProcessExecutionTask procExecTask, Optional<EndStatus> status, String execId) {
		super();
		this.procExecTask = procExecTask;
		this.status = status;
		this.execId = execId;
	}
	
}
