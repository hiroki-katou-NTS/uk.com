package nts.uk.ctx.alarm.dom.pattern;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.alarm.dom.check.checkers.AlarmListCategory;
import nts.uk.ctx.alarm.dom.check.checkers.AlarmListChecker;
import nts.uk.ctx.alarm.dom.check.context.CheckingContext;
import nts.uk.ctx.alarm.dom.check.context.period.CheckingPeriod;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * アラームリストのパターン
 */
@AllArgsConstructor
@Getter
public class AlarmListPattern implements DomainAggregate {

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
                .map(c -> require.getAlarmListChecker(c).orElseThrow(() -> new RuntimeException("not found: " + c)))
                .collect(Collectors.toList());

        val context = new CheckingContext(targetEmployeeId, checkingPeriod);

        return AtomTask.iterate(checkers, checker -> {
            return checker.check(require, context);
        });
    }

    public interface RequireCheck extends AlarmListChecker.Require {

        Optional<AlarmListChecker> getAlarmListChecker(CheckConditionKey conditionKey);
    }

}
