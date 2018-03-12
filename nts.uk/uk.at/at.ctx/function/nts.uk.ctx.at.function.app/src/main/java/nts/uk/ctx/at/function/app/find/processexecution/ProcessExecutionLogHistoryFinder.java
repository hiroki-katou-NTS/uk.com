package nts.uk.ctx.at.function.app.find.processexecution;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.app.find.processexecution.dto.ProcessExecutionLogHistoryDto;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ProcessExecutionLogHistory;
import nts.uk.ctx.at.function.dom.processexecution.repository.ProcessExecutionLogHistRepository;

@Stateless
public class ProcessExecutionLogHistoryFinder {

	@Inject
	private ProcessExecutionLogHistRepository procExecLogHstRepo;

	public ProcessExecutionLogHistoryDto find(String companyId, String execItemCd, String execId) {
		// ドメインモデル「更新処理自動実行ログ履歴」を取得する
		Optional<ProcessExecutionLogHistory> logHistoryOpt = this.procExecLogHstRepo.getByExecId(companyId, execItemCd, execId);
		if (logHistoryOpt.isPresent()) {
			return Optional.of(ProcessExecutionLogHistoryDto.fromDomain(logHistoryOpt.get())).get();
		}
		return null;
	}
}