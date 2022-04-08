/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.alarm.dom.byemployee.check.checkers.agreement.yearly;

import java.util.HashMap;
import java.util.Optional;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.Year;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.agreement.ExcessStateChecker;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.agreement.MessageForAlarm;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.agreement.TargetOfAlarmCheck;
import nts.uk.ctx.alarm.dom.byemployee.result.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.DateInfo;
import nts.uk.ctx.at.record.dom.monthly.agreement.export.AggregateAgreementTimeByYear;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeOfYear;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeYear;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.ExcessState;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.OneYearTime;


/**
 * 限度時間チェッカー
 */
public class LimitTimeChecker implements DetailOfChecker{
    private TargetOfAlarmCheck targetOfCheck;
    private AdjustRuleByYearlyLimit adjustRules;
    private ExcessStateChecker stateChecker;
    private MessageForAlarm messages;
    
    @Override
    public Optional<AlarmRecordByEmployee> check(Require require, String employeeId, Year year) {
        AgreementTimeYear agreementTime = AggregateAgreementTimeByYear.aggregate(require, employeeId, GeneralDate.today(), year, new HashMap<>());
        AgreementTimeOfYear targetTime = this.getTargetTime(agreementTime);
        
        OneYearTime threshold = this.adjustRules.adjust(targetTime.getThreshold());
        
        ExcessState excessState = threshold.check(targetTime.getTargetTime());
        if(this.stateChecker.check(excessState)) {
            return Optional.of(this.createRecord(employeeId, year, targetTime, excessState));
        } else {
            return Optional.empty();
        }
    }
    
    private AgreementTimeOfYear getTargetTime(AgreementTimeYear agreementTime) {
        switch(this.targetOfCheck) {
            case AGREEMENT_36_TIME:
                return agreementTime.getRecordTime();
            case LEGAL_MAX_TIME:
                return agreementTime.getLimitTime();
            default:
                throw new RuntimeException("アラームリストでサポートしてないチェック項目： " + this.targetOfCheck.name());
        }
    }
    
    private AlarmRecordByEmployee createRecord(String employeeId, Year year, AgreementTimeOfYear agreementTime, ExcessState state) {
        return new AlarmRecordByEmployee(
             employeeId,
             new DateInfo(year),
             AlarmListCategoryByEmployee.AGREE36_YEARLY,
             "項目名",
             "アラーム条件",
             this.messages.getMessage(state)
        );
    }
    
    public interface RequireCheck extends AggregateAgreementTimeByYear.RequireM1 {
    }
}
