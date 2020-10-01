package nts.uk.ctx.at.function.app.find.processexecution.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ProcessExecutionLogHistory;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodInfor;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.dailyperformance.AppDataInfoDaily;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.monthlyperformance.AppDataInfoMonthly;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleErrorLog;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageInfo;

/** 実行ログ詳細 */

@Getter
@Setter
public class ExecutionLogDetailDto {

    /** 日次・月次処理エラー :  エラーメッセージ情報*/
    private List<ErrMessageInfo> errMessageInfo;

    /**スケジュール作成エラー : スケジュール作成エラーログ */
    private List<ScheduleErrorLog> scheduleErrorLog;

    /** 任意期間集計エラー : 任意期間集計エラーメッセージ情報 */
    private List<AggrPeriodInfor> aggrPeriodInfor;

    /** 外部出力エラー : 外部出力結果ログ */
//    List<ExternalOutLogImport> externalOutLogImports;
//
//    /** 外部受入エラー : 外部受入エラーログ */
//    private List<ExacErrorLogImport> exacErrorLogImports;

    /**承認ルート更新（日次）エラー : 承認中間データエラーメッセージ情報（月別実績） */
    private List<AppDataInfoMonthly> appDataInfoMonthlies;

    /** 承認ルート更新（月次）エラー : 承認中間データエラーメッセージ情報（日別実績） */
    private List<AppDataInfoDaily> appDataInfoDailies;

    /**更新処理自動実行ログ履歴 : 更新処理自動実行ログ履歴 */
    private ProcessExecutionLogHistory processExecLogHistory;
}
