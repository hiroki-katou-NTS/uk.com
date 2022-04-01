package nts.uk.ctx.alarm.dom.check;

import nts.uk.ctx.alarm.dom.check.context.CheckingContext;

import java.util.List;

/**
 * チェック対象社員の条件
 */
public class TargetEmployeesFilter {

    public List<String> filter(Require require, CheckingContext context) {
        throw new RuntimeException("not implemented");
    }

    public interface Require {

    }
}
