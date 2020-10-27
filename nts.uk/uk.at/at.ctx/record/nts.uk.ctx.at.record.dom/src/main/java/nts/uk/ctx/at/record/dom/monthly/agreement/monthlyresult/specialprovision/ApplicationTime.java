package nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.persistence.internal.xr.ValueObject;

import java.util.Optional;

/**
 * 申請時間
 * @author quang.nh1
 */
@Getter
@Setter
@AllArgsConstructor
public class ApplicationTime extends ValueObject {

    /**３６協定申請種類*/
    private final TypeAgreementApplication typeAgreement;

    /**1ヶ月時間*/
    private Optional<OneMonthTime> oneMonthTime;

    /**年間時間*/
    private Optional<OneYearTime> oneYearTime;

    /**
     *	[C-0] 申請時間 (３６協定申請種類,1ヶ月時間,年間時間)
     */
    public static ApplicationTime create(TypeAgreementApplication typeAgreement, Optional<OneMonthTime> oneMonthTime,Optional<OneYearTime> oneYearTime) {
        return new ApplicationTime(typeAgreement, oneMonthTime,oneYearTime);
    }

}
