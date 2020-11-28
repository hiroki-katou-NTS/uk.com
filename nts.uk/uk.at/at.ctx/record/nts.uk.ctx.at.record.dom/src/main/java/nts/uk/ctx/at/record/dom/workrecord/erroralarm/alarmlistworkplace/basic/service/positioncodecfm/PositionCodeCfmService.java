package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.basic.service.positioncodecfm;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.adapter.workplace.EmployeeInfoImported;
import nts.uk.ctx.at.shared.dom.adapter.jobtitle.affiliate.SharedAffJobTitleAdapter;
import nts.uk.ctx.at.shared.dom.adapter.jobtitle.affiliate.AffJobTitleHistoryItemImport;
import nts.uk.ctx.at.shared.dom.adapter.jobtitle.affiliate.JobTitleHistoryImport;
import nts.uk.ctx.at.shared.dom.adapter.jobtitle.affiliate.JobTitleInfoImport;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.basic.BasicCheckName;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.extractresult.ExtractResultDto;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.DisplayMessage;
import nts.uk.ctx.at.shared.dom.scherec.alarm.alarmlistactractionresult.AlarmValueDate;
import nts.uk.ctx.at.shared.dom.scherec.alarm.alarmlistactractionresult.AlarmValueMessage;
import nts.uk.ctx.at.shared.dom.scherec.alarm.alarmlistactractionresult.MessageDisplay;
import nts.uk.shr.com.i18n.TextResource;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務実績.勤務実績.勤務実績のエラーアラーム設定.アラームリスト（職場）.マスタチェック(基本).アルゴリズム.マスタチェック(基本)の集計処理.職位コード確認
 *
 * @author Le Huu Dat
 */
@Stateless
public class PositionCodeCfmService {

    @Inject
    private SharedAffJobTitleAdapter sharedAffJobTitleAdapter;

    /**
     * 職位コード確認
     *
     * @param cid             会社ID
     * @param name            アラーム項目名
     * @param displayMessage  表示するメッセージ.
     * @param empInfosByWpMap Map＜職場ID、List＜社員情報＞＞
     * @param period          期間
     * @return List＜抽出結果＞
     */
    public List<ExtractResultDto> confirm(String cid, BasicCheckName name, DisplayMessage displayMessage,
                                          Map<String, List<EmployeeInfoImported>> empInfosByWpMap, DatePeriod period) {
        // 空欄のリスト「抽出結果」を作成する。
        List<ExtractResultDto> results = new ArrayList<>();

        // 期間から職位情報を取得
        List<JobTitleInfoImport> jobTitleInfos = sharedAffJobTitleAdapter.findByDatePeriod(cid, period);

        for (Map.Entry<String, List<EmployeeInfoImported>> empInfosByWp: empInfosByWpMap.entrySet()){

            List<String> sids = empInfosByWp.getValue().stream().map(EmployeeInfoImported::getSid).collect(Collectors.toList());
            // 期間とList<社員ID＞から職位を取得する。
            JobTitleHistoryImport jobTitleHistory = sharedAffJobTitleAdapter.getJobTitleHist(sids, period);
            List<AffJobTitleHistoryItemImport> historyItems = jobTitleHistory.getHistoryItems();

            // 取得したList＜所属職位履歴項目＞をループする。
            for (AffJobTitleHistoryItemImport historyItem: historyItems){
                // ループ中の職位IDを取得したList＜職位情報＞に存在しているかチェック
                if (jobTitleInfos.stream().anyMatch(x -> x.getJobTitleId().equals(historyItem.getJobTitleId()))) continue;

                // 存在しない場合
                EmployeeInfoImported empInfo = empInfosByWp.getValue().stream().filter(x -> x.getSid().equals(historyItem.getEmployeeId()))
                        .findFirst().orElse(null);
                if (empInfo == null) continue;

                // 「アラーム値メッセージ」を作成します。
                String message = TextResource.localize("KAL020_3", empInfo.getEmployeeCode(), empInfo.getEmployeeName());

                // ドメインオブジェクト「抽出結果」を作成してリスト「抽出結果」に追加
                ExtractResultDto result = new ExtractResultDto(new AlarmValueMessage(message),
                        new AlarmValueDate(Integer.valueOf(period.start().toString("yyyyMMdd")), Optional.empty()),
                        name.v(),
                        Optional.ofNullable(TextResource.localize("KAL020_13")),
                        Optional.of(new MessageDisplay(displayMessage.v())),
                        empInfosByWp.getKey()
                );
                results.add(result);

            }
        }

        // リスト「抽出結果」を返す。
        return results;
    }


}
