package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.daily.service.check.planunregister;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.extractresult.ExtractResultDto;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務実績.勤務実績.勤務実績のエラーアラーム設定.アラームリスト（職場）.マスタチェック（日別）.アルゴリズム.マスタチェック(日別)の集計処理.チェック処理."6.計画データ未登録をチェック"
 *
 * @author Le Huu Dat
 */
@Stateless
public class PlanUnregisterCheckService {

    /**
     * 6.計画データ未登録をチェック
     *
     * @param period          期間
     * @param dailyExtBudgets List＜日次の外部予算実績＞
     * @param actualValue     外部予算実績値
     * @return List＜抽出結果＞
     */
    public List<ExtractResultDto> check(DatePeriod period, List<Object> dailyExtBudgets, Integer actualValue) {
        List<ExtractResultDto> results = new ArrayList<>();
        // TODO Q&A 36545

        // List＜抽出結果＞を返す
        return results;
    }
}
