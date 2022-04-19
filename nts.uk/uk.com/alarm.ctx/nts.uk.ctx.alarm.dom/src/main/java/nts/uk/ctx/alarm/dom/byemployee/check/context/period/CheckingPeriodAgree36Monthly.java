/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.alarm.dom.byemployee.check.context.period;

import java.util.List;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.YearMonthPeriod;

/**
 * 36協定月次のチェック対象期間
 * @author raiki_asada
 */
public class CheckingPeriodAgree36Monthly {
 
    /**
     * 期間の開始～終了までの指定から
     * 年月の一覧を作る。
     * 既に実績になった管理期間の３６協定時間を参照するので
     * 期間を調整する話はいらない
     * @return List<YearMonth>
     */
    public List<YearMonth> calculatePeriod() {
        // TODO: あとは nか月前 / nか月先の分だけ、startとendを制御する
        YearMonth start = GeneralDate.today().yearMonth();
        YearMonth end = GeneralDate.today().yearMonth();
        
        return new YearMonthPeriod(start, end).yearMonthsBetween();
    }
}
