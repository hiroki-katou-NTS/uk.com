package nts.uk.ctx.at.record.dom.agreementbasicsettings;

import nts.uk.ctx.at.record.dom.standardtime.enums.TimeOverLimitType;
import org.eclipse.persistence.internal.xr.ValueObject;

/**
 * ValueObject:３６協定基本設定
 */
public class AgreementBasicSettings extends ValueObject {

    // 	1ヶ月
    private AgreementsOneMonth oneMonth;
    // 	1年間
    private AgreementsOneYear oneYear;
    // 複数月平均
    private AgreementsMultipleMonthsAverage multipleMonthsAverage;
    // 超過上限回数
    private TimeOverLimitType numberTimesOverLimitType;

    /**
     * 	[C-0] ３６協定基本設定(1ヶ月,1年間,複数月平均,超過上限回数)
     * @param oneMonth
     * @param oneYear
     * @param multipleMonthsAverage
     * @param numberTimesOverLimitType
     */

    public AgreementBasicSettings(AgreementsOneMonth oneMonth,AgreementsOneYear oneYear,
                                  AgreementsMultipleMonthsAverage multipleMonthsAverage,TimeOverLimitType numberTimesOverLimitType){
        this.oneMonth = oneMonth;
        this.oneYear = oneYear;
        this.multipleMonthsAverage = multipleMonthsAverage;
        this.numberTimesOverLimitType = numberTimesOverLimitType;
    }



}
