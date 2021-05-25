package nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.schedule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.ExtractionCondition;

import java.util.Collections;
import java.util.List;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.就業機能.アラーム_職場別.チェック条件.カテゴリ別のチェック条件.スケジュール／日次.スケジュール／日次のアラームチェック条件
 */

@AllArgsConstructor
@Getter
public class AlarmScheduleCheckCdt implements ExtractionCondition {

    private List<String> listExtractionIDs;

    private List<String> listOptionalIDs;

    @Override
    public List<String> getAlarmCheckWkpID() {
        return this.listExtractionIDs;
    }

    @Override
    public List<String> getListOptionalIDs() {
        return this.listOptionalIDs;
    }
}
