/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.alarm.dom.byemployee.check.checkers.agreement.yearly;

import java.util.Optional;
import lombok.AllArgsConstructor;
import nts.arc.time.calendar.Year;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCategoryByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.result.DateInfo;
import nts.uk.ctx.at.record.dom.monthly.agreement.export.AgreementExcessInfo;
import nts.uk.ctx.at.record.dom.monthly.agreement.export.GetExcessTimesYear;

/**
 *
 * @author raiki_asada
 */
@AllArgsConstructor
public class ExcuteTimesChecker implements DetailOfChecker{
    private final int threshold;
    private final String message;
    
    @Override
    public Optional<AlarmRecordByEmployee> check(Require require, String employeeId, Year year) {
        AgreementExcessInfo excessTimes = GetExcessTimesYear.get(require, employeeId, year);
        if(this.threshold < excessTimes.getExcessTimes()) {
            return Optional.of(this.createRecord(employeeId, year));
        } else {
            return Optional.empty();
        }
    }
    
    private AlarmRecordByEmployee createRecord(String employeeId, Year year) {
        return new AlarmRecordByEmployee(
             employeeId,
             new DateInfo(year),
             AlarmListCategoryByEmployee.AGREE36_YEARLY,
             "項目名",
             "アラーム条件",
             this.message
        );
    }
    
    public interface RequireCheck extends GetExcessTimesYear.RequireM1 {
    }
}
