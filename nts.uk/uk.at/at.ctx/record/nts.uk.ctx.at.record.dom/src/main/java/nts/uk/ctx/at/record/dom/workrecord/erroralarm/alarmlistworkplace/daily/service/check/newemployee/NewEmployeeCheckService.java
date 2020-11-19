package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.daily.service.check.newemployee;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.extractresult.ExtractResultDto;
import nts.uk.ctx.at.shared.dom.adapter.employee.PersonEmpBasicInfoImport;
import nts.uk.ctx.at.shared.dom.scherec.alarm.alarmlistactractionresult.AlarmValueDate;
import nts.uk.ctx.at.shared.dom.scherec.alarm.alarmlistactractionresult.AlarmValueMessage;
import nts.uk.shr.com.i18n.TextResource;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務実績.勤務実績.勤務実績のエラーアラーム設定.アラームリスト（職場）.マスタチェック（日別）.アルゴリズム.マスタチェック(日別)の集計処理.チェック処理."1.入社者をチェック"
 *
 * @author Le Huu Dat
 */
@Stateless
public class NewEmployeeCheckService {

    /**
     * 1.入社者をチェック
     *
     * @param personInfos List＜個人社員基本情報＞
     * @param period      期間
     * @return List＜抽出結果＞
     */
    public List<ExtractResultDto> check(List<PersonEmpBasicInfoImport> personInfos, DatePeriod period) {
        List<ExtractResultDto> results = new ArrayList<>();
        // Input．List＜個人社員基本情報＞をループする
        for (PersonEmpBasicInfoImport personInfo : personInfos) {
            GeneralDate jobEntryDate = personInfo.getJobEntryDate();
            // ループ中の社員の「個人社員基本情報」の入社年月日をチェック
            // Input．期間．開始日　＜＝　ループ中の入社年月日　＜＝　Input．期間．終了日
            if (period.start().beforeOrEquals(jobEntryDate) &&
                    period.end().afterOrEquals(jobEntryDate)) {
                // 抽出結果を作成
                ExtractResultDto result = new ExtractResultDto(new AlarmValueMessage(TextResource.localize("KAL020_100", personInfo.getEmployeeCode(), personInfo.getBusinessName())),
                        new AlarmValueDate(Integer.valueOf(jobEntryDate.toString("yyyyMMdd")), Optional.empty()),
                        null,
                        Optional.ofNullable(TextResource.localize("KAL020_114", jobEntryDate.toString("yyyy/MM/dd"))),
                        Optional.empty(),
                        null
                );

                // List＜抽出結果＞に作成した「抽出結果」を追加
                results.add(result);
            }
        }

        // 作成したList＜抽出結果＞を返す
        return results;
    }
}
