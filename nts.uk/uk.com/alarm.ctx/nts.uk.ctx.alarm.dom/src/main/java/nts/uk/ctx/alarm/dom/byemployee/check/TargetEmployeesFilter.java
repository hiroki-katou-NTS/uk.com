package nts.uk.ctx.alarm.dom.byemployee.check;

import nts.uk.ctx.alarm.dom.byemployee.check.context.CheckingContextByEmployee;

import java.util.List;

/**
 * チェック対象社員の条件
 */
public class TargetEmployeesFilter {

    public List<String> filter(Require require, CheckingContextByEmployee context) {
        throw new RuntimeException("not implemented");
    }

    public interface Require {

    }
}
