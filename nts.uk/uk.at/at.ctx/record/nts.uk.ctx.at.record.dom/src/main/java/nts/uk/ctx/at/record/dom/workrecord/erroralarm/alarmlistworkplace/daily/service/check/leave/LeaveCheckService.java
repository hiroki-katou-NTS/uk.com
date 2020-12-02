package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.daily.service.check.leave;

import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.adapter.workplace.EmployeeInfoImported;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.extractresult.ExtractResultDto;
import nts.uk.ctx.at.shared.dom.adapter.temporaryabsence.DateHistoryItemImport;
import nts.uk.ctx.at.shared.dom.adapter.temporaryabsence.TempAbsenceHisItemImport;
import nts.uk.ctx.at.shared.dom.adapter.temporaryabsence.TempAbsenceImport;
import nts.uk.ctx.at.shared.dom.scherec.alarm.alarmlistactractionresult.AlarmValueDate;
import nts.uk.ctx.at.shared.dom.scherec.alarm.alarmlistactractionresult.AlarmValueMessage;
import nts.uk.shr.com.i18n.TextResource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務実績.勤務実績.勤務実績のエラーアラーム設定.アラームリスト（職場）.マスタチェック（日別）.アルゴリズム.マスタチェック(日別)の集計処理.チェック処理."2.休職・休業者をチェック"
 *
 * @author Le Huu Dat
 */
public class LeaveCheckService {

    /**
     * 2.休職・休業者をチェック
     *
     * @param tempAbsence List＜汎用履歴項目＞, List<休職休業履歴項目＞
     * @param period      期間
     * @param empInfos    List＜社員情報＞
     * @return
     */
    public List<ExtractResultDto> check(TempAbsenceImport tempAbsence, DatePeriod period,
                                        List<EmployeeInfoImported> empInfos) {
        List<ExtractResultDto> results = new ArrayList<>();
        // Input．List＜社員情報＞をループする
        for (EmployeeInfoImported empInfo : empInfos) {
            // 汎用履歴項目を絞り込む
            List<DateHistoryItemImport> dateHistorysByEmp = tempAbsence.getHistories().stream()
                    .filter(x -> x.getEmployeeId().equals(empInfo.getSid()))
                    .flatMap(x -> x.getDateHistoryItems().stream())
                    .collect(Collectors.toList());
            Map<String, TempAbsenceHisItemImport> tempAbsenceHisItemByHistIdMap = tempAbsence.getHistoryItem().stream()
                    .filter(x -> x.getEmployeeId().equals(empInfo.getSid()))
                    .collect(Collectors.toMap(TempAbsenceHisItemImport::getHistoryId, x -> x));

            // 絞り込んだList＜汎用履歴項目＞をチェック
            if (CollectionUtil.isEmpty(dateHistorysByEmp) || CollectionUtil.isEmpty(tempAbsenceHisItemByHistIdMap.entrySet()))
                return results;

            // Input．List＜汎用履歴項目＞をループする
            List<String> errors = new ArrayList<>();
            for (DateHistoryItemImport dateHistory : dateHistorysByEmp) {
                if (!tempAbsenceHisItemByHistIdMap.containsKey(dateHistory.getHistoryId())) continue;
                TempAbsenceHisItemImport historyItem = tempAbsenceHisItemByHistIdMap.get(dateHistory.getHistoryId());
                // 抽出結果を作成してList＜抽出結果＞に追加
                errors.add(dateHistory.getStartDate().toString("yyyy/MM/dd") + "～" +
                        dateHistory.getEndDate().toString("yyyy/MM/dd") + "　" +
                        historyItem.getTempAbsenceFrName());
            }

            String error = String.join("、", errors);
            // 抽出結果を作成してList＜抽出結果＞に追加
            String message = TextResource.localize("KAL020_101", empInfo.getEmployeeCode() + "　" + empInfo.getEmployeeName(), error);
            ExtractResultDto result = new ExtractResultDto(new AlarmValueMessage(message),
                    new AlarmValueDate(Integer.valueOf(period.start().toString("yyyyMMdd")),
                            Optional.of(Integer.valueOf(period.end().toString("yyyyMMdd")))),
                    null,
                    Optional.ofNullable(TextResource.localize("KAL020_112")),
                    Optional.empty(),
                    null
            );
            results.add(result);
        }

        // List＜抽出結果＞を返す
        return results;
    }
}
