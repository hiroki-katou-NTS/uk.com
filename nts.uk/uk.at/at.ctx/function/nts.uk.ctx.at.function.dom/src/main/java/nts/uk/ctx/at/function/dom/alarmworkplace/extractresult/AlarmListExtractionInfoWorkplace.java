package nts.uk.ctx.at.function.dom.alarmworkplace.extractresult;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.WorkplaceCategory;

import java.util.List;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.就業機能.アラーム_職場別.抽出結果
 * アラームリスト抽出情報（職場）
 *
 * @author Le Huu Dat
 */
@Getter
public class AlarmListExtractionInfoWorkplace extends AggregateRoot {

    /**
     * チェック条件ID
     */
    private String checkConditionId;
    /**
     * 区分
     */
    private WorkplaceCategory workplaceCategory;
    /**
     * 抽出結果
     */
    private List<ExtractResult> extractResults;

}
