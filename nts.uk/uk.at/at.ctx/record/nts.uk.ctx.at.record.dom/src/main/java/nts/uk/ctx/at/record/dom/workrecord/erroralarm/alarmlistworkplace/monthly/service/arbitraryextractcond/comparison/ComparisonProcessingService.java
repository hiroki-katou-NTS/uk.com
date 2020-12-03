package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.service.arbitraryextractcond.comparison;

import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.extractresult.ExtractResultDto;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.monthly.enums.AverageTime;
import nts.uk.ctx.at.shared.dom.workrecord.alarm.attendanceitemconditions.CheckConditions;

import javax.ejb.Stateless;

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
     * @param checkConditions  チェック条件
     * @param avgTime          比較値
     * @param averageTimeValue 平均値　（Enum）
     * @return 抽出結果
     */
    public ExtractResultDto compare(String workplaceId, CheckConditions checkConditions,
                                    Double avgTime, AverageTime averageTimeValue) {
        // 「Input．比較値」とチェック条件を比較
        // checkConditions.check(avgTime)
        return null;
    }
}
