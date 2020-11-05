package nts.uk.ctx.at.function.app.find.processexecution;

import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.function.app.find.processexecution.dto.ProcessExecutionLogHistoryDto;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ProcessExecutionLogHistory;
import nts.uk.ctx.at.function.dom.processexecution.repository.ProcessExecutionLogHistRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class ProcessExecutionLogHistoryFinder {

    @Inject
    private ProcessExecutionLogHistRepository procExecLogHstRepo;

    public ProcessExecutionLogHistoryDto find(String companyId, String execItemCd, String execId) {
        // ドメインモデル「更新処理自動実行ログ履歴」を取得する
        Optional<ProcessExecutionLogHistory> logHistoryOpt = this.procExecLogHstRepo.getByExecId(companyId, execItemCd, execId);
        return logHistoryOpt.map(domain -> {
            ProcessExecutionLogHistoryDto dto = ProcessExecutionLogHistoryDto.builder().build();
            domain.setMemento(dto);
            return dto;
        }).orElse(null);
    }

    public List<ProcessExecutionLogHistoryDto> findList(String execItemCd) {
        String companyId = AppContexts.user().companyId();
        GeneralDate today = GeneralDate.today();
        List<ProcessExecutionLogHistory> lstProcessExecutionLogHistory = this.procExecLogHstRepo.getByDate(companyId,
                execItemCd, GeneralDateTime.legacyDateTime(today.date()));
        return lstProcessExecutionLogHistory.stream().map(domain -> {
            ProcessExecutionLogHistoryDto dto = ProcessExecutionLogHistoryDto.builder().build();
            domain.setMemento(dto);
            return dto;
        }).collect(Collectors.toList());
    }

    public List<ProcessExecutionLogHistoryDto> findListDateRange(String execItemCd, GeneralDate startDate, GeneralDate endDate) {
        String companyId = AppContexts.user().companyId();
        //GeneralDate.fromString(startDate,"yyyy/mm/dd").date()
        List<ProcessExecutionLogHistory> lstProcessExecutionLogHistory = this.procExecLogHstRepo.getByDateRange(companyId,
                execItemCd, GeneralDateTime.legacyDateTime(startDate.date()), GeneralDateTime.legacyDateTime(endDate.date()));
        return lstProcessExecutionLogHistory.stream().map(domain -> {
            ProcessExecutionLogHistoryDto dto = ProcessExecutionLogHistoryDto.builder().build();
            domain.setMemento(dto);
            return dto;
        }).collect(Collectors.toList());
    }

}