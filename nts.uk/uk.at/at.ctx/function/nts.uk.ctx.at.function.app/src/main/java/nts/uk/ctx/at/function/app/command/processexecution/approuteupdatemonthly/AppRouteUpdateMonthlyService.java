package nts.uk.ctx.at.function.app.command.processexecution.approuteupdatemonthly;

import nts.uk.ctx.at.function.dom.processexecution.UpdateProcessAutoExecution;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ProcessExecutionLog;

public interface AppRouteUpdateMonthlyService {
	public OutputAppRouteMonthly checkAppRouteUpdateMonthly( String execId, UpdateProcessAutoExecution procExec,
			ProcessExecutionLog procExecLog);
}
