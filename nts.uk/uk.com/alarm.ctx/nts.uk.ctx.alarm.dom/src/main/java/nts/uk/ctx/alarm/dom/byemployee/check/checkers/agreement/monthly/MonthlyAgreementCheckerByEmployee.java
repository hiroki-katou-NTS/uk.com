/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.alarm.dom.byemployee.check.checkers.agreement.monthly;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCheckerByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.agreement.MessageForAlarm;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.agreement.TargetOfAlarmCheck;
import nts.uk.ctx.alarm.dom.byemployee.check.context.CheckingContextByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.DateInfo;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.agreement.AgreementTimeOfManagePeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.ExcessState;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.AgreementOneMonthTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.OneMonthTime;

/**
 * アラームリストのチェック条件（社員別・36協定月次）
 */
@AllArgsConstructor
public class MonthlyAgreementCheckerByEmployee implements DomainAggregate, AlarmListCheckerByEmployee{
    private final TargetOfAlarmCheck target;
    private final AdjustRuleByMonthlyLimit adjustRules;
    private final MessageForAlarm messages;

    public static Builder getBuilder(TargetOfAlarmCheck target) {
        return new Builder(target);
    }
    
    @Override
    public Iterable<AlarmRecordByEmployee> check(Require require, CheckingContextByEmployee context) {
        String employeeId = context.getTargetEmployeeId();
        List<YearMonth> periods = context.getCheckingPeriod().getMonthlyAgreement().calculatePeriod();
        
        return () -> periods.stream()
                .filter(ym -> require.getAgeementTime(employeeId, ym).isPresent())
                .flatMap(ym -> {
                    AgreementTimeOfManagePeriod agreementTime = require.getAgeementTime(employeeId, ym).get();
                    AgreementTimeOfMonthly targetTime = this.getTargetTime(agreementTime);
                    OneMonthTime adjustedThreshold = this.adjustRules.adjust(targetTime.getThreshold());
                    ExcessState adjustedState = adjustedThreshold.check(targetTime.getAgreementTime());
                    return this.detect(employeeId, ym, targetTime, adjustedState)
                            .map(record -> Stream.of(record)).orElse(Stream.empty());
                }).iterator();
    }
    
    private AgreementTimeOfMonthly getTargetTime(AgreementTimeOfManagePeriod agreementTime) {
        switch(this.target) {
            case AGREEMENT_36_TIME:
                return agreementTime.getAgreementTime();
            case LEGAL_MAX_TIME:
                return agreementTime.getLegalMaxTime();
            default:
                throw new RuntimeException("アラームリストでサポートしてないチェック項目： " + this.target.name());
        }
    }
    
    private Optional<AlarmRecordByEmployee> detect(String employeeId, YearMonth yearMonth, AgreementTimeOfMonthly agreementTime, ExcessState state) {
        if(state.equals(ExcessState.NORMAL)) {
            return Optional.empty();
        }
        
        return Optional.of(new AlarmRecordByEmployee(
             employeeId,
             new DateInfo(yearMonth),
             AlarmListCategoryByEmployee.AGREE36_MONTHLY,
             "項目名",
             "アラーム条件",
             this.messages.getMessage(state)
        ));
    }
    
    public interface RequireCheck {
        Optional<AgreementTimeOfManagePeriod> getAgeementTime(String employeeId, YearMonth yearMonth);
    }
    
    public static class Builder {
        private final TargetOfAlarmCheck target;
        private final Map<ExcessState, AgreementOneMonthTime> adjustTimes = new HashMap<>();
        private final Map<ExcessState, String> messages = new HashMap<>();
        
        
        private Builder(TargetOfAlarmCheck target) {
            this.target = target;
        }
        
        public Builder put(ExcessState state, AgreementOneMonthTime adjustTime, String message) {
            this.adjustTimes.put(state, adjustTime);
            return this.put(state, message);
        }
        
        public Builder put(ExcessState state, String message) {
            this.messages.put(state, message);
            return this;
        }
        
        public MonthlyAgreementCheckerByEmployee build() {
            return new MonthlyAgreementCheckerByEmployee(
                   this.target,
                   this.buildAdjustRule(),
                   new MessageForAlarm(this.messages)
            );
        }
        
        private AdjustRuleByMonthlyLimit buildAdjustRule() {
            return new AdjustRuleByMonthlyLimit(
                Optional.ofNullable(this.adjustTimes.get(ExcessState.UPPER_LIMIT_OVER)),
                Optional.ofNullable(this.adjustTimes.get(ExcessState.ERROR_OVER)),
                Optional.ofNullable(this.adjustTimes.get(ExcessState.ALARM_OVER))
            );
        }
    }
}
