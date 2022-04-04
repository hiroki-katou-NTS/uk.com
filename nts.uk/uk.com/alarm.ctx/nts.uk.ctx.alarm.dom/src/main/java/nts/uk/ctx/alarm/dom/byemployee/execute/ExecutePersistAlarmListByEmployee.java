package nts.uk.ctx.alarm.dom.byemployee.execute;

import lombok.val;
import nts.arc.task.tran.AtomTask;
import nts.gul.collection.IteratorUtil;
import nts.uk.ctx.alarm.dom.AlarmListPatternCode;
import nts.uk.ctx.alarm.dom.byemployee.pattern.AlarmListPatternByEmployee;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.extractresult.AlarmListExtractResult;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.extractresult.ExtractEmployeeErAlData;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * アラームリスト(社員別)を実行・永続化する
 */
public class ExecutePersistAlarmListByEmployee {

    public static Iterable<AtomTask> executeAndPersist(
            Require require,
            String asyncTaskId,
            AlarmListPatternCode patternCode,
            String targetEmployeeId) {

        String executeId = asyncTaskId;

        val result = AlarmListExtractResult.createManual(
                require.getCompanyId(),
                executeId,
                require.getLoginEmployeeId());

        val resultTask = AtomTask.of(() -> require.save(result));

        val pattern = require.getAlarmListPatternByEmployee(patternCode)
                .orElseThrow(() -> new RuntimeException("not found: " + patternCode));

        val alarmsTasks = AtomTask.iterate(
                pattern.check(require, targetEmployeeId),
                alarm -> AtomTask.of(() -> {
                    require.save(alarm.toPersist(executeId));
                }));

        return IteratorUtil.merge(Arrays.asList(resultTask), alarmsTasks);
    }

    public interface Require extends AlarmListPatternByEmployee.RequireCheck {

        String getCompanyId();

        String getLoginEmployeeId();

        Optional<AlarmListPatternByEmployee> getAlarmListPatternByEmployee(AlarmListPatternCode patternCode);

        void save(AlarmListExtractResult result);

        void save(ExtractEmployeeErAlData alarm);
    }
}
