package nts.uk.ctx.alarm.byemployee.execute;

import lombok.Value;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.pattern.AlarmListPatternCode;

import java.util.List;
import java.util.stream.Collectors;

/**
 * アラームリスト(社員別)を実行するCommand
 */
@Value
public class ExecuteAlarmListByEmployeeCommand {

    String patternCode;

    List<Integer> targetCategories;

    List<String> targetEmployeeIds;

    String processStatusId;

    public AlarmListPatternCode getAlarmListPatternCode() {
        return new AlarmListPatternCode(patternCode);
    }

    public List<AlarmListCategoryByEmployee> getTargetAlarmListCategories() {
        return targetCategories.stream()
                .map(AlarmListCategoryByEmployee::valueOf)
                .collect(Collectors.toList());
    }
}
