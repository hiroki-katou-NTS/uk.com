package nts.uk.ctx.at.function.dom.processexecution.executionlog;

import java.util.Optional;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.GeneralDateTime;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.就業機能.更新処理自動実行.更新処理自動実行ログ.更新処理自動実行タスクログ
 */
@Builder
@Data
public class ExecutionTaskLog {

    /**
     * 更新処理
     */
    private ProcessExecutionTask procExecTask;

    /**
     * システムエラー内容
     */
    @Builder.Default
    private Optional<String> systemErrorDetails = Optional.empty();

    /**
     * 全体のシステムエラー状態
     */
    @Builder.Default
    private Optional<Boolean> errorSystem = Optional.empty();

    /**
     * 全体の業務エラー状態
     */
    @Builder.Default
    private Optional<Boolean> errorBusiness = Optional.empty();

    /**
     * 前回実行日時
     */
    @Builder.Default
    private Optional<GeneralDateTime> lastExecDateTime = Optional.empty();

    /**
     * 終了状態
     */
    @Builder.Default
    private Optional<EndStatus> status = Optional.empty();

    /**
     * 前回終了日時
     */
    @Builder.Default
    private Optional<GeneralDateTime> lastEndExecDateTime = Optional.empty();

	public void setProcExecTask(ProcessExecutionTask procExecTask) {
		this.procExecTask = procExecTask;
	}

	public void setSystemErrorDetails(String systemErrorDetails) {
		this.systemErrorDetails = Optional.ofNullable(systemErrorDetails);
	}

	public void setErrorSystem(Boolean errorSystem) {
		this.errorSystem = Optional.ofNullable(errorSystem);
	}

	public void setErrorBusiness(Boolean errorBusiness) {
		this.errorBusiness = Optional.ofNullable(errorBusiness);
	}

	public void setLastExecDateTime(GeneralDateTime lastExecDateTime) {
		this.lastExecDateTime = Optional.ofNullable(lastExecDateTime);
	}

	public void setStatus(EndStatus status) {
		this.status = Optional.ofNullable(status);
	}

	public void setLastEndExecDateTime(GeneralDateTime lastEndExecDateTime) {
		this.lastEndExecDateTime = Optional.ofNullable(lastEndExecDateTime);
	}
    
}
