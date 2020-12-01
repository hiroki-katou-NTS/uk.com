package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.daily.service.check.leave;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.adapter.workplace.EmployeeInfoImported;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.extractresult.ExtractResultDto;
import nts.uk.ctx.at.shared.dom.adapter.temporaryabsence.TempAbsenceHistoryImport;
import nts.uk.ctx.at.shared.dom.scherec.alarm.alarmlistactractionresult.AlarmValueDate;
import nts.uk.ctx.at.shared.dom.scherec.alarm.alarmlistactractionresult.AlarmValueMessage;
import nts.uk.shr.com.i18n.TextResource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務実績.勤務実績.勤務実績のエラーアラーム設定.アラームリスト（職場）.マスタチェック（日別）.アルゴリズム.マスタチェック(日別)の集計処理.チェック処理."2.休職・休業者をチェック"
 *
 * @author Le Huu Dat
 */
public class LeaveCheckService {

    /**
     * 2.休職・休業者をチェック
     *
     * @param tempAbsences List<休職休業履歴，休職休業履歴項目の名称>
     * @param period       期間
     * @param empInfos     List＜社員情報＞
     * @return
     */
    public List<ExtractResultDto> check(List<TempAbsenceHistoryImport> tempAbsences, DatePeriod period,
                                        List<EmployeeInfoImported> empInfos) {
        List<ExtractResultDto> results = new ArrayList<>();
        // Input．List<休職休業履歴，休職休業履歴項目の名称>をループする
        for (TempAbsenceHistoryImport tempAbsence : tempAbsences) {

            EmployeeInfoImported empInfo = empInfos.stream()
                    .filter(x -> x.getSid().contains(tempAbsence.getEmployeeId())).findFirst().get();

            // 抽出結果を作成してList＜抽出結果＞に追加 //TODO
            String message = TextResource.localize("KAL020_101", empInfo.getEmployeeCode() + "　" + empInfo.getEmployeeName());
            ExtractResultDto result = new ExtractResultDto(new AlarmValueMessage(message),
                    new AlarmValueDate(Integer.valueOf(period.start().toString("yyyyMMdd")),
                            Optional.of(Integer.valueOf(period.end().toString("yyyyMMdd")))),
                    null,
                    Optional.ofNullable(TextResource.localize("KAL020_112")),
                    Optional.empty(),
                    null
            );

            // List＜抽出結果＞に作成した「抽出結果」を追加
            results.add(result);
        }

        return results;
    }
}
