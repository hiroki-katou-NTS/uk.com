package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.workplace.service;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.extractresult.ExtractResultDto;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.workplace.WorkplaceCheckName;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.DisplayMessage;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務実績.勤務実績.勤務実績のエラーアラーム設定.アラームリスト（職場）.マスタチェック（職場）.アルゴリズム.管理者未登録を確認する
 *
 * @author Le Huu Dat
 */
@Stateless
public class UnRegisterManagerCfmService {

    /**
     * 管理者未登録を確認する
     *
     * @param workplaceCheckName アラーム項目名
     * @param displayMessage     表示するメッセージ
     * @param period             期間
     * @param workplaceIds       List＜職場ID＞
     * @return List＜抽出結果＞
     */
    public List<ExtractResultDto> confirm(WorkplaceCheckName workplaceCheckName,
                                          DisplayMessage displayMessage,
                                          DatePeriod period,
                                          List<String> workplaceIds) {
        // 空欄のリスト「抽出結果」を作成する。
        List<ExtractResultDto> results = new ArrayList<>();

        // ドメインモデル「職場管理者」を取得する。
        // TODO Q&A 36899


        // リスト「抽出結果」を返す
        return results;
    }
}
