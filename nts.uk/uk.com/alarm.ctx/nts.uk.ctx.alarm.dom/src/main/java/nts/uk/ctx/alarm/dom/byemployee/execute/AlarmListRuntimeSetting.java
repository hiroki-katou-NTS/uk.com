package nts.uk.ctx.alarm.dom.byemployee.execute;

import lombok.Value;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.pattern.AlarmListPatternCode;

import java.util.List;

/**
 * アラームリストの実行時設定
 */
@Value
public class AlarmListRuntimeSetting {

    /** パターンコード */
    AlarmListPatternCode patternCode;

    /** 対象カテゴリ一覧 */
    List<AlarmListCategoryByEmployee> targetCategories;

    /** 対象社員ID */
    String targetEmployeeId;
}
