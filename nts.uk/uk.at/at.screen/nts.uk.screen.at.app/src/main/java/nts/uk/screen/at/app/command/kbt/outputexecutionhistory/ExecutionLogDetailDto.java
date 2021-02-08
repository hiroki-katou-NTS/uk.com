package nts.uk.screen.at.app.command.kbt.outputexecutionhistory;

import lombok.Builder;
import lombok.Data;
import nts.uk.query.app.exi.ExacErrorLogQueryDto;
import nts.uk.query.app.exo.ExternalOutLogQueryDto;

import java.util.ArrayList;
import java.util.List;

/** 実行ログ詳細 */

@Data
@Builder
public class ExecutionLogDetailDto {

    /** 日次・月次処理エラー :  エラーメッセージ情報*/
	@Builder.Default
    private List<ErrMessageInfoDto> errMessageInfo = new ArrayList<>();

    /**スケジュール作成エラー : スケジュール作成エラーログ */
	@Builder.Default
    private List<ScheduleErrorLogDto> scheduleErrorLog = new ArrayList<>();

    /** 任意期間集計エラー : 任意期間集計エラーメッセージ情報 */
	@Builder.Default
    private List<AggrPeriodInforDto> aggrPeriodInfor = new ArrayList<>();

    /** 外部出力エラー : 外部出力結果ログ */
	@Builder.Default
    private List<ExternalOutLogQueryDto> externalOutLogImports = new ArrayList<>();

    /** 外部受入エラー : 外部受入エラーログ */
	@Builder.Default
    private List<ExacErrorLogQueryDto> exacErrorLogImports = new ArrayList<>();

    /**承認ルート更新（日次）エラー : 承認中間データエラーメッセージ情報（月別実績） */
	@Builder.Default
    private List<AppDataInfoMonthlyDto> appDataInfoMonthlies = new ArrayList<>();

    /** 承認ルート更新（月次）エラー : 承認中間データエラーメッセージ情報（日別実績） */
	@Builder.Default
    private List<AppDataInfoDailyDto> appDataInfoDailies = new ArrayList<>();

    /**更新処理自動実行ログ履歴 : 更新処理自動実行ログ履歴 */
    private ProcessExecutionLogHistoryDto processExecLogHistory;
}
