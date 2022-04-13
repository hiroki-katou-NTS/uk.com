package nts.uk.ctx.alarm.dom.byemployee.execute;

import lombok.val;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.alarm.dom.byemployee.pattern.AlarmListPatternByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.pattern.AlarmListPatternCode;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.extractresult.ExtractEmployeeErAlData;

import java.util.Optional;

/**
 * アラームリスト(社員別)を実行する
 */
public class ExecuteAlarmListByEmployee {

    public static Iterable<AtomTask> execute(
            Require require,
            String processStatusId,
            AlarmListRuntimeSetting runtimeSetting) {

        val pattern = require.getAlarmListPatternByEmployee(runtimeSetting.getPatternCode())
                .orElseThrow(() -> new RuntimeException("not found: " + runtimeSetting.getPatternCode()));

        return AtomTask.iterate(
                pattern.check(require, runtimeSetting.getTargetCategories(), runtimeSetting.getTargetEmployeeId()),
                alarm -> AtomTask.of(() -> {
                    require.save(alarm.toPersist(processStatusId));
                }));
    }

    public interface Require extends AlarmListPatternByEmployee.RequireCheck {

        String getCompanyId();

        String getLoginEmployeeId();

        Optional<AlarmListPatternByEmployee> getAlarmListPatternByEmployee(AlarmListPatternCode patternCode);

        void save(ExtractEmployeeErAlData alarm);
    }
}
