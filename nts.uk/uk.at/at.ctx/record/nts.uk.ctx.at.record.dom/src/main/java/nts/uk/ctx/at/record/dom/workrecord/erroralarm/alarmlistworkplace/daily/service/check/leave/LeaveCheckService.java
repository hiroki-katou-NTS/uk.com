package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.daily.service.check.leave;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.adapter.workplace.EmployeeInfoImported;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.extractresult.ExtractResultDto;

import java.util.ArrayList;
import java.util.List;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務実績.勤務実績.勤務実績のエラーアラーム設定.アラームリスト（職場）.マスタチェック（日別）.アルゴリズム.マスタチェック(日別)の集計処理.チェック処理."2.休職・休業者をチェック"
 *
 * @author Le Huu Dat
 */
public class LeaveCheckService {

    /**
     * 2.休職・休業者をチェック
     *
     * @param empLeaves List<休職休業履歴，休職休業履歴項目の名称>
     * @param period    期間
     * @param empInfos  List＜社員情報＞
     * @return
     */
    public List<ExtractResultDto> check(List<Object> empLeaves, DatePeriod period,
                                        List<EmployeeInfoImported> empInfos) {
        List<ExtractResultDto> results = new ArrayList<>();
        // TODO
        for (Object empLeave : empLeaves) {

        }

        return results;
    }
}
