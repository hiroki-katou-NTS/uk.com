/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.alarm.dom.byemployee.check.checkers.prospect.count.vacation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.export.query.publicholiday.GetRemainingNumberPublicHolidayService;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.export.query.publicholiday.param.AggrResultOfPublicHoliday;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.InterimRemainMngMode;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.closure.ClosureMonth;

/**
 * アラームリスト用の公休日数を取得する
 *  アラームリストで呼び出す際には以下のようにしている
 * 　・実績のみ参照区分(月次モード orその他)　-> 暫定のものも参照するので、その他
 * 　・上書きフラグ　　　　　　　　　　　　　　-> empty
 * 　・上書き用暫定管理データ　　　　　　　　　 -> empty
 * 　・作成元区分　　　　　　　　　　　　　　　　-> empty
 * 　・上書き対象期間　　　　　　　　　　　　　　-> empty
 * @author raiki_asada
 */
public class GetPublicHoldayForAlarmList {
    
    public static double get(Require require, String companyId, String employeeId, ClosureMonth month) {
        AggrResultOfPublicHoliday aggrResult = GetRemainingNumberPublicHolidayService.getPublicHolidayRemNumWithinPeriod(
                companyId, employeeId, Arrays.asList(month.getYearMonth()), month.defaultPeriod().end(), InterimRemainMngMode.OTHER, 
                Optional.empty(), new ArrayList<>(), Optional.empty(), Optional.empty(), new CacheCarrier(), require);
        return aggrResult.createPublicHolidayRemainData(employeeId, month.getYearMonth(), ClosureId.valueOf(month.closureId()), month.closureDate())
                .getPublicHolidayday().v();
    }
    
    public interface Require extends GetRemainingNumberPublicHolidayService.RequireM1{
    }
}
