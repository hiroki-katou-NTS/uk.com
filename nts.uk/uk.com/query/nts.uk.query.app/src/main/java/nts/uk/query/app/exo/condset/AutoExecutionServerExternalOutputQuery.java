package nts.uk.query.app.exo.condset;

import java.util.Collections;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.task.AsyncTaskInfoRepository;
import nts.arc.task.AsyncTaskStatus;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.exio.dom.exo.execlog.ExecutionForm;
import nts.uk.ctx.exio.dom.exo.exoutsummaryservice.CreateExOutTextService;
import nts.uk.ctx.exio.dom.exo.exoutsummaryservice.ExOutSetting;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.外部入出力.外部出力.アルゴリズム.サーバ外部出力自動実行.サーバ外部出力自動実行.サーバ外部出力自動実行
 */
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Stateless
public class AutoExecutionServerExternalOutputQuery {
	
	@Inject
	private AsyncTaskInfoRepository asyncTaskInfoRepository;

	@Inject
	private CreateExOutTextService createExOutTextService;

	public void processAutoExecution(String conditionCd, DatePeriod period, GeneralDate baseDate, Integer categoryId,
			String execId) {
		// 外部出力処理IDを採番する
		String processingId = execId;
		ExOutSetting exOutSetting = new ExOutSetting(conditionCd, AppContexts.user().userId(), categoryId,
				period.start(), period.end(), baseDate, processingId, true, Collections.emptyList());
		exOutSetting.setExecuteForm(ExecutionForm.AUTOMATIC_EXECUTION);
		String taskId = createExOutTextService.start(exOutSetting).getTaskId();
		// Wait until export service is done
		AsyncTaskStatus taskStatus;
		do {
			taskStatus = this.asyncTaskInfoRepository.getStatus(taskId);
		} while (taskStatus.equals(AsyncTaskStatus.PENDING) || taskStatus.equals(AsyncTaskStatus.RUNNING));
	}
}
