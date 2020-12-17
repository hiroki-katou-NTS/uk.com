package nts.uk.ctx.at.schedule.dom.budget.premium;

import lombok.AllArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.history.strategic.PersistentResidentHistory;

import java.util.List;

/**
 * AggregateRoot: 人件費計算設定の履歴
 */
@AllArgsConstructor
public class HistPersonCostCalculation extends AggregateRoot implements PersistentResidentHistory<DateHistoryItem, DatePeriod, GeneralDate> {

    // 会社ID
    private String companyId;

    // 履歴: 年月日期間の汎用履歴項目
    private List<DateHistoryItem> historyItems;


    @Override
    public List<DateHistoryItem> items() {
        return historyItems;
    }
}
