package nts.uk.ctx.pr.core.dom.wageprovision.statementlayout;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.history.strategic.PersistentHistory;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import java.util.List;

/**
* 明細書レイアウト履歴
*/
@Getter
public class StatementLayoutHist extends AggregateRoot implements PersistentHistory<YearMonthHistoryItem, YearMonthPeriod,YearMonth> {
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * 明細書コード
    */
    private StatementCode statementCode;

    /**
     * 履歴
     */
    private List<YearMonthHistoryItem> history;
    
    public StatementLayoutHist(String cid, String statementCd, List<YearMonthHistoryItem> history) {
        this.statementCode = new StatementCode(statementCd);
        this.cid = cid;
        this.history = history;
    }

    @Override
    public List<YearMonthHistoryItem> items() {
        return history;
    }
}
