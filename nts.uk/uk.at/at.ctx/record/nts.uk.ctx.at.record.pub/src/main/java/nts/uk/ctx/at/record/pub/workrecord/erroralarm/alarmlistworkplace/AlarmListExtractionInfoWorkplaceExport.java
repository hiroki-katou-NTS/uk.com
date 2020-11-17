package nts.uk.ctx.at.record.pub.workrecord.erroralarm.alarmlistworkplace;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * アラームリスト抽出情報（職場）
 *
 * @author Le Huu Dat
 */
@Getter
@AllArgsConstructor
public class AlarmListExtractionInfoWorkplaceExport {

    /**
     * チェック条件ID
     */
    private String checkConditionId;
    /**
     * 区分
     */
    private int workplaceCategory;
    /**
     * 抽出結果
     */
    private List<ExtractResultExport> extractResults;

}
