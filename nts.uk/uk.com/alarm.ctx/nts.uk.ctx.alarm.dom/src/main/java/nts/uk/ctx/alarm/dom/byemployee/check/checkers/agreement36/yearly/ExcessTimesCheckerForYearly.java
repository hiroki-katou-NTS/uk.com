/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.alarm.dom.byemployee.check.checkers.agreement.yearly;

import java.util.Optional;
import lombok.AllArgsConstructor;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.Year;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.DateInfo;
import nts.uk.ctx.at.record.dom.monthly.agreement.export.GetExcessTimesYear;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.agreement.AgreementTimeOfManagePeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.ExcessState;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.OneMonthTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.setting.AgreementOperationSetting;
import nts.uk.shr.com.context.AppContexts;

/**
 * 超過回数のチェック条件
 * @author raiki_asada
 */
@AllArgsConstructor
public class ExcessTimesCheckerForYearly implements DetailOfChecker{
    private final int threshold;
    private final String message;
    
    @Override
    public Optional<AlarmRecordByEmployee> check(Require require, String employeeId, Year year) {
        long excessTimes = this.calclateExcessTimes(require, employeeId, year);
        if(this.threshold <= excessTimes) {
            return Optional.of(this.createRecord(employeeId, year, excessTimes));
        } else {
            return Optional.empty();
        }
    }
    
    private long calclateExcessTimes(Require require, String employeeId, Year year) {
        String cid = AppContexts.user().companyId();
        Optional<AgreementOperationSetting> setting = require.agreementOperationSetting(cid);
        return setting.map(s -> {
            YearMonthPeriod period = s.getYearMonthPeriod(year);
            return period.stream().map(ym -> require.agreementTimeOfManagePeriod(employeeId, ym))
                    .filter(Optional::isPresent).map(Optional::get)
                    .filter(agreementTime -> {
                        //月間限度時間の法定上限を超過したものだけをカウントする
                        OneMonthTime monthlyThreshold = agreementTime.getAgreementTime().getThreshold();
                        return monthlyThreshold.check(agreementTime.getAgreementTime().getAgreementTime())
                                .equals(ExcessState.UPPER_LIMIT_OVER);
                    }).count();
        }).orElse((long)0);
    }
    
    private AlarmRecordByEmployee createRecord(String employeeId, Year year, long excessTimes) {
        return new AlarmRecordByEmployee(
             employeeId,
             new DateInfo(year),
             AlarmListCategoryByEmployee.AGREE36_YEARLY,
             "項目名",
             "超過回数　" + excessTimes,
             this.message
        );
    }
    
    public interface RequireCheck extends GetExcessTimesYear.RequireM1 {
        Optional<AgreementOperationSetting> agreementOperationSetting(String cid);
        Optional<AgreementTimeOfManagePeriod> getAgeementTime(String employeeId, YearMonth yearMonth);
    }
}
