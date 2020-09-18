package nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.record.dom.standardtime.enums.TimeOverLimitType;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.AgreementOneMonthTime;

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
    private Optional<AgreementOneMonthTime> maximumTimeYear; //TODO thiếu primivativeValue

    /**超過上限回数*/
    private Optional<TimeOverLimitType> exceedUpperLimit;

    public static ExcessErrorContent create(ErrorClassification errorClassification, Optional<AgreementOneMonthTime> maximumTimeMonth,
                              Optional<AgreementOneMonthTime> maximumTimeYear, Optional<TimeOverLimitType> exceedUpperLimit) {

        return new ExcessErrorContent(errorClassification,maximumTimeMonth,maximumTimeYear,exceedUpperLimit);
    }
}
