package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.daily.service.check.planunregister;

import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.adapter.shift.estimate.company.CompanyEstablishmentImport;
import nts.uk.ctx.at.record.dom.adapter.workschedule.budgetcontrol.budgetperformance.ExBudgetDailyImport;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.extractresult.ExtractResultDto;
import nts.uk.ctx.at.shared.dom.scherec.alarm.alarmlistactractionresult.AlarmValueDate;
import nts.uk.ctx.at.shared.dom.scherec.alarm.alarmlistactractionresult.AlarmValueMessage;
import nts.uk.ctx.at.shared.dom.scherec.alarm.alarmlistactractionresult.MessageDisplay;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
     * @param exBudgetDailies List＜日次の外部予算実績＞
     * @param actualValue     外部予算実績値
     * @return List＜抽出結果＞
     */
    public List<ExtractResultDto> check(DatePeriod period, List<ExBudgetDailyImport> exBudgetDailies, Integer actualValue) {
        List<ExtractResultDto> results = new ArrayList<>();

        // Input．期間をループする
        period.datesBetween().forEach(date -> {
            // 日次の外部予算実績を探す
            List<ExBudgetDailyImport> exFilter = exBudgetDailies.stream().filter(x -> x.getYmd().equals(date) && x.getActualValue() == actualValue)
                    .collect(Collectors.toList());
            if (!CollectionUtil.isEmpty(exFilter)) return;

            // 抽出結果を作成
            String actualValueName = "";
            switch (actualValue) {
                case 0:
                    actualValueName = TextResource.localize("KAL020_107");
                    break;
                case 1:
                    actualValueName = TextResource.localize("KAL020_108");
                    break;
                case 2:
                    actualValueName = TextResource.localize("KAL020_109");
                    break;
            }
            String message = TextResource.localize("KAL020_106", actualValueName);
            ExtractResultDto result = new ExtractResultDto(new AlarmValueMessage(message),
                    new AlarmValueDate(Integer.valueOf(period.start().toString("yyyyMMdd")), Optional.empty()),
                    null,
                    Optional.ofNullable(TextResource.localize("KAL020_115")),
                    Optional.empty(),
                    null);

            // List＜抽出結果＞に作成した抽出結果を追加
            results.add(result);
        });

        // List＜抽出結果＞を返す
        return results;
    }
}
