package nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.applicationapproval;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.ExtractionCondition;

import java.util.Collections;
import java.util.List;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.就業機能.アラーム_職場別.チェック条件.カテゴリ別のチェック条件.申請承認.申請承認のアラームチェック条件
 */

@AllArgsConstructor
@Getter
public class AlarmAppApprovalCheckCdt implements ExtractionCondition {

    private List<String> alarmCheckWkpID;

    @Override
    public List<String> getAlarmCheckWkpID() {
        return this.alarmCheckWkpID;
    }

    @Override
    public List<String> getListOptionalIDs() {
        return Collections.emptyList();
    }
}
