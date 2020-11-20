package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.daily.service.check.stampafterretirement;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.extractresult.ExtractResultDto;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.shared.dom.adapter.employee.PersonEmpBasicInfoImport;
import nts.uk.ctx.at.shared.dom.scherec.alarm.alarmlistactractionresult.AlarmValueDate;
import nts.uk.ctx.at.shared.dom.scherec.alarm.alarmlistactractionresult.AlarmValueMessage;
import nts.uk.shr.com.i18n.TextResource;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務実績.勤務実績.勤務実績のエラーアラーム設定.アラームリスト（職場）.マスタチェック（日別）.アルゴリズム.マスタチェック(日別)の集計処理.チェック処理."11.退職日よりも後の打刻をチェック"
 *
 * @author Le Huu Dat
 */
@Stateless
public class StampAfterRetirementCheckService {

    /**
     * 11.退職日よりも後の打刻をチェック
     *
     * @param personInfos    List＜個人社員基本情報＞
     * @param stampsByEmpMap Map＜社員ID、List＜打刻＞＞
     * @param period         期間
     * @return List＜抽出結果＞
     */
    public List<ExtractResultDto> check(List<PersonEmpBasicInfoImport> personInfos,
                                        Map<String, List<Stamp>> stampsByEmpMap, DatePeriod period) {
        List<ExtractResultDto> results = new ArrayList<>();

        // Input．List＜個人社員基本情報＞をループする
        for (PersonEmpBasicInfoImport personInfo : personInfos) {
            GeneralDate retirementDate = personInfo.getRetirementDate();
            // ループ中の社員のList＜打刻＞を探す
            if (!stampsByEmpMap.containsKey(personInfo.getEmployeeId())) continue;
            List<Stamp> stamps = stampsByEmpMap.get(personInfo.getEmployeeId());

            // 退職年月日と打刻日を比較
            if (period.start().addMonths(-1).beforeOrEquals(retirementDate) && period.end().afterOrEquals(retirementDate)) {
                List<Stamp> stampErrors = stamps.stream().filter(x -> x.getStampDateTime().toDate().after(retirementDate))
                        .collect(Collectors.toList());
                if (!CollectionUtil.isEmpty(stampErrors)) {
                    List<String> datetimeErrors = stampErrors.stream().map(x -> x.getStampDateTime().toString("yyyy/MM/dd HH:mm:ss"))
                            .collect(Collectors.toList());
                    String datetimeError = String.join("、", datetimeErrors);
                    // 抽出結果を作成
                    String message = TextResource.localize("KAL020_117", personInfo.getEmployeeCode() + "　" + personInfo.getBusinessName());
                    ExtractResultDto result = new ExtractResultDto(new AlarmValueMessage(message),
                            new AlarmValueDate(Integer.valueOf(period.start().toString("yyyyMMdd")), Optional.empty()),
                            null,
                            Optional.ofNullable(TextResource.localize("KAL020_118", retirementDate.toString("yyyy/MM/dd"), datetimeError)),
                            Optional.empty(),
                            null
                    );

                    // List＜抽出結果＞に作成した抽出結果を追加
                    results.add(result);
                }
            }
        }

        // List＜抽出結果＞を返す
        return results;
    }
}
