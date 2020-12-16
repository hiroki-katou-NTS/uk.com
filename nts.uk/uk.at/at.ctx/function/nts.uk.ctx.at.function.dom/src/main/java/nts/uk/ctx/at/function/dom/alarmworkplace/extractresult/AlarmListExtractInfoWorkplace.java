package nts.uk.ctx.at.function.dom.alarmworkplace.extractresult;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
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
public class AlarmListExtractInfoWorkplace extends AggregateRoot {
    /**
     * チェック条件ID
     */
    private String checkConditionId;

    /**
     * 処理ID
     */
    @Setter
    private String processingId;

    /**
     * 区分
     */
    private WorkplaceCategory category;

    /**
     * カテゴリ名
     */
    @Setter
    private String categoryName;

    /**
     * 抽出結果
     */
    private List<ExtractResult> extractResults;

    public AlarmListExtractInfoWorkplace(String checkConditionId, WorkplaceCategory category, List<ExtractResult> extractResults) {
        this.checkConditionId = checkConditionId;
        this.category = category;
        this.extractResults = extractResults;
    }


}
