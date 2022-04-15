/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.alarm.dom.byemployee.check.checkers.agreement.yearly;

import java.util.Optional;
import nts.arc.time.calendar.Year;
import nts.uk.ctx.alarm.dom.byemployee.result.AlarmRecordByEmployee;

/**
 *
 * チェック条件詳細
 */
public interface DetailOfChecker {
    Optional<AlarmRecordByEmployee> check(Require require, String employeeId, Year year);
    
    interface Require extends ExcessStateCheckerForYearly.RequireCheck, ExcessTimesCheckerForYearly.RequireCheck{
    }
}
