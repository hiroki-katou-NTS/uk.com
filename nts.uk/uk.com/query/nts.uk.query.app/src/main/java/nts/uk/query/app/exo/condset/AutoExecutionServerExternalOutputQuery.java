package nts.uk.query.app.exo.condset;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.layer.app.file.storage.FileStorage;
import nts.arc.task.AsyncTaskInfo;
import nts.arc.task.AsyncTaskInfoRepository;
import nts.arc.task.AsyncTaskStatus;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSet;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSetRepository;
import nts.uk.ctx.exio.dom.exo.execlog.ExecutionForm;
import nts.uk.ctx.exio.dom.exo.execlog.ExterOutExecLog;
import nts.uk.ctx.exio.dom.exo.execlog.ExterOutExecLogRepository;
import nts.uk.ctx.exio.dom.exo.execlog.ExternalOutLog;
import nts.uk.ctx.exio.dom.exo.execlog.ExternalOutLogRepository;
import nts.uk.ctx.exio.dom.exo.execlog.ProcessingClassification;
import nts.uk.ctx.exio.dom.exo.exoutsummaryservice.CreateExOutTextService;
import nts.uk.ctx.exio.dom.exo.exoutsummaryservice.ExOutSetting;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.外部入出力.外部出力.アルゴリズム.サーバ外部出力自動実行.サーバ外部出力自動実行.サーバ外部出力自動実行
 */
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Stateless
public class AutoExecutionServerExternalOutputQuery {

	@Inject
	private AsyncTaskInfoRepository asyncTaskInfoRepository;

	@Inject
	private ExterOutExecLogRepository exterOutExecLogRepository;

	@Inject
	private ExternalOutLogRepository externalOutLogRepository;

	@Inject
	private StdOutputCondSetRepository stdOutputCondSetRepository;
	
	@Inject
	private CreateExOutTextService createExOutTextService;

	@Inject
	private FileStorage fileStorage;

	public Optional<String> processAutoExecution(String cid, String conditionCd, DatePeriod period,
			GeneralDate baseDate, Integer categoryId, String execId) {
		Optional<StdOutputCondSet> optStdOutputCondSet = this.stdOutputCondSetRepository
				.getStdOutputCondSetById(cid, conditionCd);
		// 外部出力処理IDを採番する
		String processingId = execId;
		ExOutSetting exOutSetting = new ExOutSetting(conditionCd, null, categoryId, period.start(), period.end(),
				baseDate, processingId, true, Collections.emptyList());
		exOutSetting.setExecuteForm(ExecutionForm.AUTOMATIC_EXECUTION);
		exOutSetting.setCid(cid);
		optStdOutputCondSet.ifPresent(data -> exOutSetting.setConditionSetName(data.getConditionSetName().v()));
		String taskId = createExOutTextService.start(exOutSetting).getTaskId();
		// Wait until export service is done
		AsyncTaskStatus taskStatus;
		do {
			taskStatus = this.asyncTaskInfoRepository.getStatus(taskId);
		} while (taskStatus.equals(AsyncTaskStatus.PENDING) || taskStatus.equals(AsyncTaskStatus.RUNNING));
		// Update file size if export is successful
		Optional<ExterOutExecLog> optResult = this.exterOutExecLogRepository.getExterOutExecLogById(cid, processingId);
		optResult.ifPresent(result -> {
			result.getFileId().ifPresent(fileId -> {
				fileStorage.getInfo(fileId).ifPresent(fileInfo -> {
					this.exterOutExecLogRepository.update(cid, processingId, fileInfo.getOriginalSize());
				});
			});
		});
		// Check whether errors have occurred or not
		// Check business exception when perform createExOutTextService
		List<ExternalOutLog> logList = this.externalOutLogRepository.getExternalOutLogById(cid, execId,
				ProcessingClassification.ERROR.value);
		Optional<String> msg = logList.stream().map(item -> item.getErrorContent().orElse(null))
				.filter(Objects::nonNull).findAny();
		if (msg.isPresent()) {
			return msg;
		}
		// Check runtime exception thrown from AsyncTask
		if (!taskStatus.equals(AsyncTaskStatus.COMPLETED)) {
			Optional<AsyncTaskInfo> optInfo = this.asyncTaskInfoRepository.find(taskId);
			if (optInfo.isPresent()) {
				return Optional.ofNullable(optInfo.get().getError().getMessage());
			}
		}
		return Optional.empty();
	}
}
