package nts.uk.ctx.core.dom.socialinsurance.socialinsuranceoffice;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.history.strategic.ContinuousHistory;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import java.util.List;

/**
 * 社会保険用都道府県履歴
 */
@Getter
public class SocialInsurancePrefectureHistory extends AggregateRoot implements ContinuousHistory<YearMonthHistoryItem, YearMonthPeriod, YearMonth> {

    /**
     * 履歴
     */
    private List<YearMonthHistoryItem> history;

    public SocialInsurancePrefectureHistory(List<YearMonthHistoryItem> history) {
        super();
        this.history = history;
    }

    @Override
    public List<YearMonthHistoryItem> items() {
        return this.history;
    }
}
