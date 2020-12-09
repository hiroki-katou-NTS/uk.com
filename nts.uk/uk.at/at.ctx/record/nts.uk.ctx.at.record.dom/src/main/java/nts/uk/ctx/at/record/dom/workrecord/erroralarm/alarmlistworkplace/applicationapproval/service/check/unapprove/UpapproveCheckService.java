package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.applicationapproval.service.check.unapprove;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.adapter.function.alarmworkplace.EmployeeInfoImport;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.extractresult.ExtractResultDto;

import javax.ejb.Stateless;
import java.util.List;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務実績.勤務実績.勤務実績のエラーアラーム設定.アラームリスト（職場）.申請承認.アルゴリズム.申請承認の集計処理.固定抽出条件をチェック.未承認をチェックする
 *
 * @author Le Huu Dat
 */
@Stateless
public class UpapproveCheckService {

    /**
     * 未承認をチェックする
     *
     * @param employeeInfo     List＜社員情報＞
     * @param period           期間
     * @param approvalPhaseNum 承認フェーズ番号
     * @return 抽出結果
     */
    public ExtractResultDto check(List<EmployeeInfoImport> employeeInfo, DatePeriod period, int approvalPhaseNum) {
        // 対象者と期間から承認ルートインスタンスを取得する
        //(Lấy [承認ルートインスタンス] từ employee và period)
        // TODO Q&A 37635


        return null;
    }
}
