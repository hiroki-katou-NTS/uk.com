/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.alarm.dom.byemployee.check.checkers.agreement.yearly;

import java.util.HashMap;
import java.util.Optional;
import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.Year;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.agreement.ExcessStateChecker;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.agreement.TargetOfAlarm;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.agreement.Threshold;
import nts.uk.ctx.alarm.dom.byemployee.result.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.DateInfo;
import nts.uk.ctx.at.record.dom.monthly.agreement.export.AggregateAgreementTimeByYear;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeOfYear;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeYear;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.AgreementOneYearTime;


/**
 * 超過状態をチェックする条件
 */
@AllArgsConstructor
public class ExcessStateCheckerForYearly implements DetailOfChecker{
    private final TargetOfAlarm targetOfCheck;
    private final AdjustRuleByYearlyLimit adjustRules;
    private final ExcessStateChecker<AgreementOneYearTime> checker;
    
    @Override
    public Optional<AlarmRecordByEmployee> check(Require require, String employeeId, Year year) {
        AgreementTimeYear agreementTime = AggregateAgreementTimeByYear.aggregate(require, employeeId, GeneralDate.today(), year, new HashMap<>());
        AgreementTimeOfYear targetTime = this.getTargetTime(agreementTime);
        Threshold<AgreementOneYearTime> threshold = this.getThreshold(targetTime);
        return checker.check(threshold, targetTime.getTargetTime())
                .getRecord(employeeId, new DateInfo(year), targetOfCheck);
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
    
    private Threshold<AgreementOneYearTime> getThreshold(AgreementTimeOfYear targetTime) {
        switch(this.targetOfCheck) {
            case AGREEMENT_36_TIME:
                return this.adjustRules.adjust(targetTime.getThreshold().getErAlTime());
            case LEGAL_MAX_TIME:
                return this.adjustRules.adjust(targetTime.getThreshold());
            default:
                throw new RuntimeException("アラームリストでサポートしてないチェック項目： " + this.targetOfCheck.name());
        }
    }
    
    public interface RequireCheck extends AggregateAgreementTimeByYear.RequireM1 {
    }
}
