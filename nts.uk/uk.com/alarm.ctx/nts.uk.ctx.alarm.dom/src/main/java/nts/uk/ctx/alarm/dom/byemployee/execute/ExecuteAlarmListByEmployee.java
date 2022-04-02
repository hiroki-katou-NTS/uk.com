package nts.uk.ctx.alarm.dom.byemployee.execute;

import lombok.val;
import nts.gul.collection.IteratorUtil;
import nts.uk.ctx.alarm.dom.AlarmListPatternCode;
import nts.uk.ctx.alarm.dom.byemployee.check.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.pattern.AlarmListPatternByEmployee;

import java.util.List;
import java.util.Optional;

/**
 * アラームリスト(社員別)を実行する
 */
public class ExecuteAlarmListByEmployee {

    /**
     * 実行する
     * @param require
     * @param patternCode
     * @param targetEmployeeIds
     * @return
     */
    public static Iterable<AlarmRecordByEmployee> execute(
            Require require,
            AlarmListPatternCode patternCode,
            List<String> targetEmployeeIds) {

        val pattern = require.getAlarmListPatternByEmployee(patternCode)
                .orElseThrow(() -> new RuntimeException("not found: " + patternCode));

        return IteratorUtil.iterableFlatten(targetEmployeeIds, employeeId -> pattern.check(require, employeeId));
    }


    public interface Require extends AlarmListPatternByEmployee.RequireCheck {

        Optional<AlarmListPatternByEmployee> getAlarmListPatternByEmployee(AlarmListPatternCode patternCode);
    }
}
