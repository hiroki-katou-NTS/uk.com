package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.history.strategic.PersistentHistory;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import java.util.List;

/**
* 賃金テーブル履歴
*/
@Getter
public class WageTableHist extends AggregateRoot implements PersistentHistory<YearMonthHistoryItem, YearMonthPeriod,YearMonth> {

    /**
    * 会社ID
    */
    private String cid;

    /**
     * 賃金テーブルコード
     */
    private WageTableCode wageTableCode;

    /**
     * 履歴
     */
    private List<YearMonthHistoryItem> history;

    public WageTableHist(String cid, String wageTableCode, List<YearMonthHistoryItem> history) {
        this.wageTableCode = new WageTableCode(wageTableCode);
        this.cid = cid;
        this.history = history;
    }

    @Override
    public List<YearMonthHistoryItem> items() {
        return history;
    }
}
