package nts.uk.ctx.alarm.dom.byemployee.execute;

import lombok.val;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.alarm.dom.AlarmListPatternCode;
import nts.uk.ctx.alarm.dom.byemployee.pattern.AlarmListPatternByEmployee;

import java.util.ArrayList;
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
    public static Iterable<AtomTask> execute(
            Require require,
            AlarmListPatternCode patternCode,
            List<String> targetEmployeeIds) {

        val pattern = require.getAlarmListPatternByEmployee(patternCode)
                .orElseThrow(() -> new RuntimeException("not found: " + patternCode));

        return AtomTask.iterate(targetEmployeeIds, employeeId -> {

            // TODO: 本当はbundleするんじゃなくてflatすべきだと思う
            List<AtomTask> tasks = new ArrayList<>();
            pattern.check(require, employeeId).forEach(tasks::add);

            return AtomTask.bundle(tasks);
        });
    }


    public interface Require extends AlarmListPatternByEmployee.RequireCheck {

        Optional<AlarmListPatternByEmployee> getAlarmListPatternByEmployee(AlarmListPatternCode patternCode);
    }
}
