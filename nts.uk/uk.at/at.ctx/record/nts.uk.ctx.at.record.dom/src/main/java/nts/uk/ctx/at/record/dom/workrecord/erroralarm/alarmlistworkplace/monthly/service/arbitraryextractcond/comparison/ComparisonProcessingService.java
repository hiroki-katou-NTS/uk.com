package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.service.arbitraryextractcond.comparison;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.extractresult.ExtractResultDto;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.ExtractionMonthlyCon;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.enums.AverageNumberOfDays;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.enums.AverageNumberOfTimes;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.enums.AverageRatio;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.enums.AverageTime;
import nts.uk.ctx.at.shared.dom.scherec.alarm.alarmlistactractionresult.AlarmValueDate;
import nts.uk.ctx.at.shared.dom.scherec.alarm.alarmlistactractionresult.AlarmValueMessage;
import nts.uk.ctx.at.shared.dom.scherec.alarm.alarmlistactractionresult.MessageDisplay;
import nts.uk.shr.com.i18n.TextResource;

import javax.ejb.Stateless;
import java.util.Optional;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務実績.勤務実績.勤務実績のエラーアラーム設定.アラームリスト（職場）.月次のアラームチェック.アルゴリズム.月次の集計処理.任意抽出条件をチェック.比較処理
 *
 * @author Le Huu Dat
 */
@Stateless
public class ComparisonProcessingService {

    /**
     * 比較処理
     *
     * @param workplaceId      職場ID
     * @param condition        チェック条件
     * @param avgTime          比較値
     * @param averageTimeValue 平均値　（Enum）
     * @param ym               年月
     * @return 抽出結果
     */
    public ExtractResultDto compare(String workplaceId, ExtractionMonthlyCon condition,
                                    Double avgTime, AverageTime averageTimeValue, YearMonth ym) {
        // 「Input．比較値」とチェック条件を比較
        boolean check = condition.getCheckConditions().check(avgTime, c -> avgTime);

        // 抽出結果を作成 // TODO
        ExtractResultDto result = new ExtractResultDto(new AlarmValueMessage(TextResource.localize("KAL020_402")),
                new AlarmValueDate(ym.v(), Optional.empty()),
                condition.getMonExtracConName().v(),
                Optional.ofNullable(TextResource.localize("KAL020_408")),
                Optional.of(new MessageDisplay(condition.getMessageDisp().v())),
                workplaceId);

        // 作成した抽出結果を返す
        return result;
    }

    /**
     * 比較処理
     *
     * @param workplaceId   職場ID
     * @param condition     チェック条件
     * @param avgTime       比較値
     * @param averageNumDay 平均値　（Enum）
     * @param ym            年月
     * @return 抽出結果
     */
    public ExtractResultDto compare(String workplaceId, ExtractionMonthlyCon condition,
                                    Double avgTime, AverageNumberOfDays averageNumDay, YearMonth ym) {
        // 「Input．比較値」とチェック条件を比較
        boolean check = condition.getCheckConditions().check(avgTime, c -> avgTime);

        // 抽出結果を作成 // TODO
        ExtractResultDto result = new ExtractResultDto(new AlarmValueMessage(TextResource.localize("KAL020_402")),
                new AlarmValueDate(ym.v(), Optional.empty()),
                condition.getMonExtracConName().v(),
                Optional.ofNullable(TextResource.localize("KAL020_408")),
                Optional.of(new MessageDisplay(condition.getMessageDisp().v())),
                workplaceId);

        // 作成した抽出結果を返す
        return result;
    }

    /**
     * 比較処理
     *
     * @param workplaceId     職場ID
     * @param condition       チェック条件
     * @param avgTime         比較値
     * @param averageNumTimes 平均値　（Enum）
     * @param ym              年月
     * @return 抽出結果
     */
    public ExtractResultDto compare(String workplaceId, ExtractionMonthlyCon condition,
                                    Double avgTime, AverageNumberOfTimes averageNumTimes, YearMonth ym) {
        // 「Input．比較値」とチェック条件を比較
        boolean check = condition.getCheckConditions().check(avgTime, c -> avgTime);

        // 抽出結果を作成 // TODO
        ExtractResultDto result = new ExtractResultDto(new AlarmValueMessage(TextResource.localize("KAL020_402")),
                new AlarmValueDate(ym.v(), Optional.empty()),
                condition.getMonExtracConName().v(),
                Optional.ofNullable(TextResource.localize("KAL020_408")),
                Optional.of(new MessageDisplay(condition.getMessageDisp().v())),
                workplaceId);

        // 作成した抽出結果を返す
        return result;
    }

    /**
     * 比較処理
     *
     * @param workplaceId  職場ID
     * @param condition    チェック条件
     * @param avgTime      比較値
     * @param averageRatio 平均値　（Enum）
     * @param ym           年月
     * @return 抽出結果
     */
    public ExtractResultDto compare(String workplaceId, ExtractionMonthlyCon condition,
                                    Double avgTime, AverageRatio averageRatio, YearMonth ym) {
        // 「Input．比較値」とチェック条件を比較
        boolean check = condition.getCheckConditions().check(avgTime, c -> avgTime);

        // 抽出結果を作成 // TODO
        ExtractResultDto result = new ExtractResultDto(new AlarmValueMessage(TextResource.localize("KAL020_402")),
                new AlarmValueDate(ym.v(), Optional.empty()),
                condition.getMonExtracConName().v(),
                Optional.ofNullable(TextResource.localize("KAL020_408")),
                Optional.of(new MessageDisplay(condition.getMessageDisp().v())),
                workplaceId);

        // 作成した抽出結果を返す
        return result;
    }
}
