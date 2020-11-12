package nts.uk.ctx.at.function.app.command.processexecution;

import lombok.Builder;
import lombok.Data;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.function.dom.processexecution.createlogfileexecution.CalTimeRangeDateTimeToString;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ExecutionTaskLog;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ProcessExecutionTask;

@Data
@Builder
public class ProcessExecutionTaskLogCommand {

    /* 更新処理 */
    private Integer taskId;

    /* システムエラー内容 */
    private String systemErrorDetails;

    /* 全体のシステムエラー状態*/
    private Boolean errorSystem;

    /* 全体の業務エラー状態*/
    private Boolean errorBusiness;

    /* 前回実行日時 */
    private GeneralDateTime lastExecDateTime;

    /* 終了状態 */
    private Integer status;

    /* 前回終了日時*/
    private GeneralDateTime lastEndExecDateTime;

    public static ProcessExecutionTaskLogCommand fromDomain(ExecutionTaskLog domain) {
        return ProcessExecutionTaskLogCommand.builder()
                .taskId(domain.getProcExecTask().value)
                .status(domain.getStatus().map(e -> e.value).orElse(null))
                .lastExecDateTime(domain.getLastExecDateTime().orElse(null))
                .lastEndExecDateTime(domain.getLastEndExecDateTime().orElse(null))
                .errorSystem(domain.getErrorSystem().orElse(null))
                .errorBusiness(domain.getErrorBusiness().orElse(null))
                .build();
    }
}
