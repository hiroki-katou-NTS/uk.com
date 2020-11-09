package nts.uk.ctx.at.function.app.find.processexecution.dto;

import lombok.Builder;
import lombok.Data;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.function.dom.processexecution.createlogfileexecution.CalTimeRangeDateTimeToString;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ExecutionTaskLog;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ProcessExecutionTask;

@Data
@Builder
public class ProcessExecutionTaskLogDto {
	
	private static final String HAVE_ERROR = "あり";
	private static final String NOT_HAVE_ERROR = "なし";

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
    private String status;

    /* 前回終了日時*/
    private GeneralDateTime lastEndExecDateTime;

    private String taskName;

    /* 終了状態 */
    private Integer statusCd;

    private String rangeDateTime;

    private String errorSystemText;

    private String errorBusinessText;

    public static ProcessExecutionTaskLogDto fromDomain(ExecutionTaskLog domain) {
        String rangeDateTime = "";
        if (domain.getLastExecDateTime().isPresent() && domain.getLastEndExecDateTime().isPresent()) {
            rangeDateTime = CalTimeRangeDateTimeToString.calTimeExec(domain.getLastExecDateTime().get(), domain.getLastEndExecDateTime().get());
        }
        return ProcessExecutionTaskLogDto.builder()
                .taskId(domain.getProcExecTask().value)
                .taskName(EnumAdaptor.valueOf(domain.getProcExecTask().value, ProcessExecutionTask.class).name)
                .statusCd(domain.getStatus().map(e -> e.value).orElse(null))
                .status(domain.getStatus().map(e -> e.name).orElse(null))
                .lastExecDateTime(domain.getLastExecDateTime().orElse(null))
                .lastEndExecDateTime(domain.getLastEndExecDateTime().orElse(null))
                .errorSystem(domain.getErrorSystem().orElse(null))
                .errorBusiness(domain.getErrorBusiness().orElse(null))
                .rangeDateTime(rangeDateTime)
                .errorSystemText(domain.getErrorSystem().map(error -> error ? HAVE_ERROR : NOT_HAVE_ERROR).orElse(null))
                .errorBusinessText(domain.getErrorBusiness().map(error -> error ? HAVE_ERROR : NOT_HAVE_ERROR).orElse(null))
                .build();
    }
}
