package nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.workplace;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.ExtractionCondition;

import java.util.List;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.就業機能.アラーム_職場別.チェック条件.カテゴリ別のチェック条件.マスタチェック(職場).マスタチェック(職場)のアラームチェック条件
 */

@AllArgsConstructor
@Getter
public class AlarmMasterWkpCheckCdt implements ExtractionCondition {

    private List<String> alarmCheckWkpID;

}
