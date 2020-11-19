package nts.uk.ctx.at.record.dom.workrecord.erroralarm.alarmlistworkplace.extractresult;

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
public class AlarmListExtractionInfoWorkplaceDto {

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
    private List<ExtractResultDto> extractResults;

}
