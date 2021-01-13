package nts.uk.ctx.at.function.app.command.processexecution.approuteupdatedaily;

import nts.uk.ctx.at.function.dom.processexecution.UpdateProcessAutoExecution;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ProcessExecutionLog;
/**
 * 承認ルート更新（日次）
 * @author tutk
 *
 */
public interface AppRouteUpdateDailyService {
	public OutputAppRouteDaily checkAppRouteUpdateDaily( String execId, UpdateProcessAutoExecution procExec,
			ProcessExecutionLog procExecLog);
}
