/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.alarm.dom.byemployee.check.checkers.agree36.yearly;

import java.util.List;
import lombok.Value;
import lombok.val;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.arc.time.calendar.Year;
import nts.gul.collection.IteratorUtil;
import nts.uk.ctx.alarm.dom.AlarmListCheckerCode;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCheckerByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.context.CheckingContextByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.conditionvalue.AlarmListConditionValue;

/**
 * アラームリストのチェック条件（社員別・36協定年次）
 */
@Value
public class Agree36YearlyCheckerByEmployee implements DomainAggregate, AlarmListCheckerByEmployee {
    private String companyId;
    private AlarmListCheckerCode checkerCode;
    private String name;
    private List<AlarmListConditionValue<
            ConditionValueAgree36YearlyCheckerByEmployee, 
            ConditionValueAgree36YearlyCheckerByEmployee.Context>> conditionValues;

    @Override
    public Iterable<AlarmRecordByEmployee> check(Require require, CheckingContextByEmployee context) {
        String employeeId = context.getTargetEmployeeId();
        Year year = context.getCheckingPeriod().getYearlyAgreement().calculatePeriod();

        val conditionValueContext = new ConditionValueAgree36YearlyCheckerByEmployee.Context(require, employeeId, year);
        return IteratorUtil.iterableFilter(conditionValues, cv -> cv.checkIfEnabled(conditionValueContext));
    }

    public static interface RequireCheck extends ConditionValueAgree36YearlyCheckerByEmployee.Require {
    }
}
