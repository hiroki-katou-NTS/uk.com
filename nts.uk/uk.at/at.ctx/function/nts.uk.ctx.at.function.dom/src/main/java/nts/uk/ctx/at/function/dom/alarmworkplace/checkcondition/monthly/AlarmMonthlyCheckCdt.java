package nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.monthly;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.ExtractionCondition;

import java.util.List;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.就業機能.アラーム_職場別.チェック条件.カテゴリ別のチェック条件.月次.月次のアラームチェック条件
 */

@AllArgsConstructor
@Getter
public class AlarmMonthlyCheckCdt implements ExtractionCondition {

    private List<String> listExtractionIDs;

    private List<String> listOptionalIDs;

}
