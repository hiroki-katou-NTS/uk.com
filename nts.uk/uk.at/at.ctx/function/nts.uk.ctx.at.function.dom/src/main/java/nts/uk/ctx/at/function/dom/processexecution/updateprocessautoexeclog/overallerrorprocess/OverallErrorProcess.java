package nts.uk.ctx.at.function.dom.processexecution.updateprocessautoexeclog.overallerrorprocess;

import javax.ejb.Stateless;

import nts.uk.ctx.at.function.dom.processexecution.executionlog.ProcessExecutionLog;
/**
 * 全体エラー状況確認処理
 * @author tutk
 *
 */
@Stateless
public class OverallErrorProcess {
	
	public ErrorConditionOutput overallErrorProcess(ProcessExecutionLog procExecLog) {
//		boolean isHasErrorSystem = false;
//		boolean isHasErrorBussiness = false;
//		//【INPUT「更新処理自動実行ログ」．各処理の終了状態．システムエラー状態】をチェックする
//		for(ExecutionTaskLog taskLog : procExecLog.getTaskLogList()) {
//			if(taskLog.getErrorSystem().isPresent() && taskLog.getErrorSystem().get()) {
//				isHasErrorSystem = true;
//				break;
//			}
//		}
//		//【INPUT「更新処理自動実行ログ」．各処理の終了状態．業務エラー状態】をチェックする
//		for(ExecutionTaskLog taskLog : procExecLog.getTaskLogList()) {
//			if(taskLog.getErrorBusiness().isPresent() && taskLog.getErrorBusiness().get() == true) {
//				isHasErrorBussiness = true;
//				break;
//			}
//		}
		
		//【INPUT「更新処理自動実行ログ」．各処理の終了状態．業務エラー状態】をチェックする
		boolean isHasErrorBussiness = procExecLog.getTaskLogList().stream()
				.filter(taskLog -> taskLog.getErrorBusiness().isPresent() && taskLog.getErrorBusiness().get())
				.findFirst()
				.isPresent();
		//【INPUT「更新処理自動実行ログ」．各処理の終了状態．システムエラー状態】をチェックする
		boolean isHasErrorSystem = procExecLog.getTaskLogList().stream()
				.filter(taskLog -> taskLog.getErrorSystem().isPresent() && taskLog.getErrorSystem().get())
				.findFirst()
				.isPresent();
		return new ErrorConditionOutput(isHasErrorBussiness,isHasErrorSystem);
	}
}
