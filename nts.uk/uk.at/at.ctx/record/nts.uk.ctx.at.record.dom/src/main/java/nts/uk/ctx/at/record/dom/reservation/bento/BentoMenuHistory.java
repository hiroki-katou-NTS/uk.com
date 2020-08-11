package nts.uk.ctx.at.record.dom.reservation.bento;


import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.history.strategic.PersistentHistory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BentoMenuHistory extends AggregateRoot implements PersistentHistory<DateHistoryItem, DatePeriod, GeneralDate> {
    /**
     * 会社ID
     */
    public String companyId;

    /**
     * The Date History Item.
     */
    // 履歴項目
    @Getter
    private List<DateHistoryItem> historyItems;

    @Override
    public List<DateHistoryItem> items() {
        return historyItems;
    }
    public BentoMenuHistory(String companyId, List<DateHistoryItem> historyItems) {
        super();
        this.companyId = companyId;

        this.historyItems = historyItems;
    }
    public static BentoMenuHistory toDomain(String cid, DateHistoryItem item){
        return new BentoMenuHistory(cid, Arrays.asList(item));
    }

}
