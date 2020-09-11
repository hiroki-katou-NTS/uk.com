package nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.AgreementOneMonthTime;

/**
 * 申請時点の時間外時間
 * @author quang.nh1
 */
@Getter
@AllArgsConstructor
public class Overtime {

    /** 対象年度の時間外時間*/
    private AgreementOneMonthTime overtimeHoursOfYear;

    /** 対象月度の時間外時間*/
    private AgreementOneMonthTime overtimeHoursOfMonth;
}
