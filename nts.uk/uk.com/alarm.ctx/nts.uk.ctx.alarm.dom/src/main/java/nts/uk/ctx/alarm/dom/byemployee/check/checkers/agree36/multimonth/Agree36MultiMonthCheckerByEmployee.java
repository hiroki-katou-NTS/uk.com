/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.alarm.dom.byemployee.check.checkers.agree36.multimonth;

import java.util.HashMap;
import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.alarm.dom.AlarmListAlarmMessage;
import nts.uk.ctx.alarm.dom.AlarmListCheckerCode;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCheckerByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.context.CheckingContextByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.DateInfo;
import nts.uk.ctx.alarm.dom.conditionvalue.ConditionValueExpression;
import nts.uk.ctx.at.record.dom.monthly.agreement.export.AggregateAgreementTimeByYM;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

/**
 * アラームリストのチェック条件（社員別・36協定複数月）
 */
@Value
public class Agree36MultiMonthCheckerByEmployee implements DomainAggregate, AlarmListCheckerByEmployee {

    private String companyId;
    private AlarmListCheckerCode checkerCode;
    private String name;
    
    private ConditionValueExpression expression;

    private String itemName;
    
    private boolean enabled;
    private AlarmListAlarmMessage message;

    @Override
    public Iterable<AlarmRecordByEmployee> check(Require require, CheckingContextByEmployee context) {
        String employeeId = context.getTargetEmployeeId();
        YearMonth criteria = null;
        
        return () -> AggregateAgreementTimeByYM.aggregate(require, employeeId, GeneralDate.today(), criteria, new HashMap<>()).getAverageTimes()
                .stream().filter(averageTime -> this.expression.matches(averageTime.getAverageTime().v()))
                .map(averageTime -> this.createRecord(employeeId, averageTime.getPeriod(), averageTime.getAverageTime())).iterator();
    }
    
    private AlarmRecordByEmployee createRecord(String employeeId, YearMonthPeriod period, AttendanceTimeMonth averageTime) {
        return new AlarmRecordByEmployee(
                employeeId,
                new DateInfo(period),
                AlarmListCategoryByEmployee.AGREE36_MULT_MONTH,
                this.itemName,
                expression.toText(),
                String.format("実績: %.2f", (double)averageTime.v()),
                message);
    }
    
    public interface RequireCheck extends AggregateAgreementTimeByYM.RequireM1 {
    }

}
