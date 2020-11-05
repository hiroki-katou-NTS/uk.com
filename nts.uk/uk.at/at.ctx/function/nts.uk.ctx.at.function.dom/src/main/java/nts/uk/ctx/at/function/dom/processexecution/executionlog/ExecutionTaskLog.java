package nts.uk.ctx.at.function.dom.processexecution.executionlog;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDateTime;

import java.util.Optional;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.就業機能.更新処理自動実行.更新処理自動実行ログ.更新処理自動実行タスクログ
 */
@Data
@Builder
public class ExecutionTaskLog {

    /**
     * 更新処理
     */
    private ProcessExecutionTask procExecTask;

    /**
     * システムエラー内容
     */
    private Optional<String> systemErrorDetails;

    /**
     * 全体のシステムエラー状態
     */
    private Optional<Boolean> errorSystem;

    /**
     * 全体の業務エラー状態
     */
    private Optional<Boolean> errorBusiness;

    /**
     * 前回実行日時
     */
    private Optional<GeneralDateTime> lastExecDateTime;

    /**
     * 終了状態
     */
    private Optional<EndStatus> status;

    /**
     * 前回終了日時
     */
    private Optional<GeneralDateTime> lastEndExecDateTime;
}
