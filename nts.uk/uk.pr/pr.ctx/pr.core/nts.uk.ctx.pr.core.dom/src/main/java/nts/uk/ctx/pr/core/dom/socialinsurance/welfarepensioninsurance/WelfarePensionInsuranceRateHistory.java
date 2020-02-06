package nts.uk.ctx.pr.core.dom.socialinsurance.welfarepensioninsurance;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice.SocialInsuranceOfficeCode;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.history.strategic.ContinuousHistory;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import java.util.List;

/**
 * 厚生年金保険料率履歴
 */
@Getter
public class WelfarePensionInsuranceRateHistory extends AggregateRoot implements ContinuousHistory<YearMonthHistoryItem, YearMonthPeriod, YearMonth> {

    /**
     * 会社ID
     */
    private String cid;

    /**
     * 社会保険事業所コード
     */
    private SocialInsuranceOfficeCode socialInsuranceOfficeCode;

    /**
     * 履歴
     */
    private List<YearMonthHistoryItem> history;

    /**
     * 厚生年金保険料率履歴
     *
     * @param cid                     会社ID
     * @param socialInsuranceOfficeCd 社会保険事業所コード
     * @param history                 履歴
     */
    public WelfarePensionInsuranceRateHistory(String cid, String socialInsuranceOfficeCd, List<YearMonthHistoryItem> history) {
        this.cid = cid;
        this.socialInsuranceOfficeCode = new SocialInsuranceOfficeCode(socialInsuranceOfficeCd);
        this.history = history;
    }

    @Override
    public List<YearMonthHistoryItem> items() {
        return this.history;
    }
}
