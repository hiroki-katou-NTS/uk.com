package nts.uk.ctx.alarm.dom.byemployee.pattern;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.alarm.dom.AlarmListCheckerCode;
import nts.uk.ctx.alarm.dom.AlarmListPatternCode;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCheckerByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.context.CheckingContextByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.context.period.CheckingPeriod;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * アラームリストのパターン
 */
@AllArgsConstructor
@Getter
public class AlarmListPatternByEmployee implements DomainAggregate {

    private final String companyId;

    private final AlarmListPatternCode code;

    private List<CheckConditionKey> conditions;

    private CheckingPeriod checkingPeriod;

    /**
     * チェックする
     * @param require
     * @param targetEmployeeId 対象社員ID
     * @return
     */
    public Iterable<AtomTask> check(RequireCheck require, String targetEmployeeId) {

        val checkers = conditions.stream()
                .map(c -> require.getAlarmListChecker(c.getCategory(), c.getCheckerCode())
                        .orElseThrow(() -> new RuntimeException("not found: " + c)))
                .collect(Collectors.toList());

        val context = new CheckingContextByEmployee(targetEmployeeId, checkingPeriod);

        return AtomTask.iterate(checkers, checker -> {
            return checker.check(require, context);
        });
    }

    public interface RequireCheck extends AlarmListCheckerByEmployee.Require {

        Optional<AlarmListCheckerByEmployee> getAlarmListChecker(AlarmListCategoryByEmployee category, AlarmListCheckerCode checkerCode);
    }

}
