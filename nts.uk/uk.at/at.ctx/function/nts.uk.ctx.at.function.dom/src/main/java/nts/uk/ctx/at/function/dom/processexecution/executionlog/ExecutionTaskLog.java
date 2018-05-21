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
@AllArgsConstructor
public class ExecutionTaskLog {
	/* 更新処理 */
	private ProcessExecutionTask procExecTask;
	
	/* 終了状態 */
	private Optional<EndStatus> status;
}
