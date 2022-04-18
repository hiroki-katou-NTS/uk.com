/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.alarm.dom.byemployee.check.context.period;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;

/**
 *　36協定複数月平均のチェック対象期間
 * @author raiki_asada
 */
public class CheckingPeriodAgree36MulthMonth {
    
    /**
     * 本年度 or n年前とシステム日付から年を作る
     * @return year
     */
    public YearMonth calculatePeriod() {
        // TODO: あとは nか月前にする処理が必要
        return GeneralDate.today().yearMonth();
    }
}
