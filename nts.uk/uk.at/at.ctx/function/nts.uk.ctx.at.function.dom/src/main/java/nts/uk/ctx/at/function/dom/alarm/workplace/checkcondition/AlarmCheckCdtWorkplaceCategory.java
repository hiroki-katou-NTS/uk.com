package nts.uk.ctx.at.function.dom.alarm.workplace.checkcondition;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionCode;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionName;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.就業機能.アラーム_職場別.チェック条件.カテゴリ別アラームチェック条件(職場別)
 */

@Getter
@Setter
@NoArgsConstructor
public class AlarmCheckCdtWorkplaceCategory extends AggregateRoot {

    // カテゴリ
    private WorkplaceCategory category;

    // アラームチェック条件コード
    private AlarmCheckConditionCode code;

    // 会社ID
    private String cID;

    // 名称
    private AlarmCheckConditionName name;

    // 抽出条件
    private ExtractionCondition condition;

}
