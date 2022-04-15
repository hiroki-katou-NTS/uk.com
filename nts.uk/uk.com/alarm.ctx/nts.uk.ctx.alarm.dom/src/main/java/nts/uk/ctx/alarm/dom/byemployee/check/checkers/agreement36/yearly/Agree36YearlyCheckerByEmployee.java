/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.alarm.dom.byemployee.check.checkers.agreement.yearly;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import lombok.AllArgsConstructor;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.arc.time.calendar.Year;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCheckerByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.agreement.MessageForAlarm;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.agreement.TargetOfAlarm;
import nts.uk.ctx.alarm.dom.byemployee.check.context.CheckingContextByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.AlarmRecordByEmployee;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.ExcessState;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.AgreementOneYearTime;

/**
 * アラームリストのチェック条件（社員別・36協定年次）
 */
@AllArgsConstructor
public class YearlyAgreementCheckerByEmployee implements DomainAggregate, AlarmListCheckerByEmployee{
    private final DetailOfChecker details;
    
    public static Builder getBuilder(TargetOfAlarm target) {
        return new Builder(target);
    }
    
    public static YearlyAgreementCheckerByEmployee create(int threshold, String message) {
        DetailOfChecker detail = new ExcessTimesCheckerForYearly(threshold, message);
        return new YearlyAgreementCheckerByEmployee(detail);
    }
    
    @Override
    public Iterable<AlarmRecordByEmployee> check(Require require, CheckingContextByEmployee context) {
        String employeeId = context.getTargetEmployeeId();
        Year year = context.getCheckingPeriod().getYearlyAgreement().calculatePeriod();
        
        List<AlarmRecordByEmployee> result = new ArrayList<>();
        this.details.check(require, employeeId, year).ifPresent(record -> result.add(record));
        
        return () -> result.iterator();
    }
    
    public static interface RequireCheck extends DetailOfChecker.Require{
    }
 
    public static class Builder {
        private final TargetOfAlarm target;
        private final Map<ExcessState, AgreementOneYearTime> adjustTimes = new HashMap<>();
        private final Map<ExcessState, String> messages = new HashMap<>();
        private final Set<ExcessState> targets = new HashSet<>();
        
        private Builder(TargetOfAlarm target) {
            this.target = target;
        }
        
        public Builder put(ExcessState state, AgreementOneYearTime adjustTime, String message) {
            this.adjustTimes.put(state, adjustTime);
            return this.put(state, message);
        }
        
        public Builder put(ExcessState state, String message) {
            this.messages.put(state, message);
            this.targets.add(state);
            return this;
        }
        
        public YearlyAgreementCheckerByEmployee build() {
            DetailOfChecker detail = new ExcessStateCheckerForYearly(
                  this.target,
                  this.buildAdjustRule(),
                  new YearlyAlarmChecker(this.targets, new MessageForAlarm(this.messages))
            );
            
            return new YearlyAgreementCheckerByEmployee(detail);
        }
        
        private AdjustRuleByYearlyLimit buildAdjustRule() {
            return new AdjustRuleByYearlyLimit(
                Optional.ofNullable(this.adjustTimes.get(ExcessState.UPPER_LIMIT_OVER)),
                Optional.ofNullable(this.adjustTimes.get(ExcessState.ERROR_OVER)),
                Optional.ofNullable(this.adjustTimes.get(ExcessState.ALARM_OVER))
            );
        }
    }
}
