package nts.uk.ctx.pr.core.dom.socialinsurance.contribution;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice.SocialInsuranceOfficeCode;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.history.strategic.ContinuousHistory;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import java.util.List;

/**
 * 拠出金率履歴
 */
@Getter
public class ContributionRateHistory extends AggregateRoot implements ContinuousHistory<YearMonthHistoryItem, YearMonthPeriod, YearMonth> {

    /**
     * 会社ID
     */
    private String companyId;

    /**
     * 社会保険事業所コード
     */
    private SocialInsuranceOfficeCode socialInsuranceCode;

    /**
     * 履歴
     */
    private List<YearMonthHistoryItem> history;

    /**
     * 拠出金率履歴
     *
     * @param companyId           会社ID
     * @param socialInsuranceCode 社会保険事業所コード
     * @param history             履歴
     */
    public ContributionRateHistory(String companyId, String socialInsuranceCode, List<YearMonthHistoryItem> history) {
        super();
        this.companyId = companyId;
        this.socialInsuranceCode = new SocialInsuranceOfficeCode(socialInsuranceCode);
        this.history = history;
    }

    @Override
    public List<YearMonthHistoryItem> items() {
        return this.history;
    }
}
