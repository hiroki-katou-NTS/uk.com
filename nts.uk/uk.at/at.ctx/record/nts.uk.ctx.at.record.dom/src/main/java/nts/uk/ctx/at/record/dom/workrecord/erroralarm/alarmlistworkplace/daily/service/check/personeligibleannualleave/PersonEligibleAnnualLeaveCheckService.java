package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.daily.service.check.personeligibleannualleave;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.extractresult.ExtractResultDto;
import nts.uk.ctx.at.shared.dom.adapter.employee.PersonEmpBasicInfoImport;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務実績.勤務実績.勤務実績のエラーアラーム設定.アラームリスト（職場）.マスタチェック（日別）.アルゴリズム.マスタチェック(日別)の集計処理.チェック処理."4.年休付与対象者をチェック"
 *
 * @author Le Huu Dat
 */
@Stateless
public class PersonEligibleAnnualLeaveCheckService {

    /**
     * 4.年休付与対象者をチェック
     *
     * @param cid         会社ID
     * @param personInfos List＜個人社員基本情報＞
     * @param period      期間
     * @return List＜抽出結果＞
     */
    public List<ExtractResultDto> check(String cid, List<PersonEmpBasicInfoImport> personInfos, DatePeriod period) {
        List<ExtractResultDto> results = new ArrayList<>();
        // Input．List＜個人社員基本情報＞をループする
        for (PersonEmpBasicInfoImport personInfo : personInfos) {
            // 次回年休付与日を取得する
            // TODO Q&A 36663

        }

        // 作成したList＜抽出結果＞を返す
        return results;
    }
}
