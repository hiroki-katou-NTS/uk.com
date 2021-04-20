package nts.uk.ctx.at.function.dom.alarmworkplace.extractresult;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.WorkplaceCategory;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.就業機能.アラーム_職場別.抽出結果
 * アラームリスト抽出情報（職場）
 *
 * @author Le Huu Dat
 */
@Getter
public class AlarmListExtractInfoWorkplace extends AggregateRoot {

    /**
     * Id
     */
    @Setter
    private String recordId;

    /**
     * チェック条件ID
     */
    private String checkConditionId;

    /**
     * 処理ID
     */
    @Setter
    private String processId;

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
    private ExtractResult extractResult;

    public AlarmListExtractInfoWorkplace(String checkConditionId, WorkplaceCategory category, ExtractResult extractResult) {
        this.checkConditionId = checkConditionId;
        this.category = category;
        this.extractResult = extractResult;
    }
}
