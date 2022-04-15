/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.alarm.dom.byemployee.check.context.period;

import nts.arc.time.calendar.Year;

/**
 * 36協定年次のチェック対象期間
 * @author raiki_asada
 */
public class CheckingPeriodYearlyAgreement {
    
    /**
     * 本年度 or n年前とシステム日付から年を作る
     * @return year
     */
    public Year calculatePeriod() {
        throw new RuntimeException("not implemented");
    }
}
