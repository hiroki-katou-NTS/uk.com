package nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult;

import java.util.Optional;

/**
 * 申請時間
 * @author quang.nh1
 */
public class ApplicationTime {

    /**３６協定申請種類*/
    private TypeAgreementApplication typeAgreement;

    /**1ヶ月時間*/
    private Optional<OneMonthTime> oneMonthTime;

    /**年間時間*/
    private Optional<OneYearTime> oneYearTime;

}
