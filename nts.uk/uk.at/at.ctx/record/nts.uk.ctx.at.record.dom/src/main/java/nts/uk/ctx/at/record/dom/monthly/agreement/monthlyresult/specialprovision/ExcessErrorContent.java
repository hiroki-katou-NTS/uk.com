package nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.AgreementOneMonthTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.AgreementOneYearTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.AgreementOverMaxTimes;

import java.util.Optional;

/**
 * 超過エラー内容
 * @author quang.nh1
 */
@Getter
@AllArgsConstructor
public class ExcessErrorContent {

    /**エラー区分*/
    private ErrorClassification errorClassification;

    /**１ヶ月上限時間*/
    private Optional<AgreementOneMonthTime> maximumTimeMonth;

    /**1年上限間時間*/
    private Optional<AgreementOneYearTime> maximumTimeYear;

    /**超過上限回数*/
    private Optional<AgreementOverMaxTimes> exceedUpperLimit;

    public static ExcessErrorContent create(ErrorClassification errorClassification, Optional<AgreementOneMonthTime> maximumTimeMonth,
                                            Optional<AgreementOneYearTime> maximumTimeYear, Optional<AgreementOverMaxTimes> exceedUpperLimit) {

        return new ExcessErrorContent(errorClassification,maximumTimeMonth,maximumTimeYear,exceedUpperLimit);
    }
}
