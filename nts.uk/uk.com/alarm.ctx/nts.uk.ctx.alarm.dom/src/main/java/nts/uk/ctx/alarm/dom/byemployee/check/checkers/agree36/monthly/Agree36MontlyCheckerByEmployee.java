/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.alarm.dom.byemployee.check.checkers.agree36.monthly;

import java.util.Arrays;
import java.util.List;
import lombok.Value;
import lombok.val;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.arc.time.YearMonth;
import nts.gul.collection.IteratorUtil;
import nts.uk.ctx.alarm.dom.AlarmListCheckerCode;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCheckerByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.context.CheckingContextByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.conditionvalue.AlarmListConditionValue;

/**
 * アラームリストのチェック条件（社員別・36協定月次）
 */
@Value
public class Agree36MontlyCheckerByEmployee implements DomainAggregate, AlarmListCheckerByEmployee {
    private String companyId;
    private AlarmListCheckerCode checkerCode;
    private String name;
    private List<AlarmListConditionValue<
            ConditionValueAgree36MonthlyCheckerByEmployee,
            ConditionValueAgree36MonthlyCheckerByEmployee.Context>> conditionValues;
    
    @Override
    public Iterable<AlarmRecordByEmployee> check(Require require, CheckingContextByEmployee context) {
        String employeeId = context.getTargetEmployeeId();
        List<YearMonth> periods = context.getCheckingPeriod().getMonthlyAgreement().calculatePeriod();

        return IteratorUtil.iterableFlatten(periods, period -> {
            val conditionValueContext = new ConditionValueAgree36MonthlyCheckerByEmployee.Context(require, employeeId, period);
            return IteratorUtil.iterableFilter(conditionValues, cv -> cv.checkIfEnabled(conditionValueContext));
        });
    }

    public interface RequireCheck extends ConditionValueAgree36MonthlyCheckerByEmployee.Require {
    }
}
