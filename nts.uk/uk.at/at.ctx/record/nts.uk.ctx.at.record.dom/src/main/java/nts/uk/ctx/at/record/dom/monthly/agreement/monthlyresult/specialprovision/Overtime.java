package nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.AgreementOneMonthTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.AgreementOneYearTime;

/**
 * 申請時点の時間外時間
 * @author quang.nh1
 */
@Getter
@AllArgsConstructor
public class Overtime {

    /** 対象月度の時間外時間*/
    private final AgreementOneMonthTime overtimeHoursOfMonth;

    /** 対象年度の時間外時間*/
    private final AgreementOneYearTime overtimeHoursOfYear;

    /**
     * [C-0] 申請時点の時間外時間 (対象月度の時間外時間,対象年度の時間外時間)
     */
    public static Overtime create(AgreementOneMonthTime overtimeHoursOfMonth, AgreementOneYearTime overtimeHoursOfYear) {
        return new Overtime(overtimeHoursOfMonth, overtimeHoursOfYear);
    }
}
