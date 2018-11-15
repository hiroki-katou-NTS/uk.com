package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.history.strategic.ContinuousHistory;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import java.util.List;
import java.util.Optional;

/**
 * 賃金テーブル
 */
@Getter
public class WageTableHistory extends AggregateRoot implements ContinuousHistory<YearMonthHistoryItem, YearMonthPeriod, YearMonth> {

    /**
     * 会社ID
     */
    private String cid;

    /**
     * 賃金テーブルコード
     */
    private WageTableCode wageTableCode;

    /**
     * 有効期間
     */
    private List<YearMonthHistoryItem> history;

    @Override
    public List<YearMonthHistoryItem> items() {
        return this.history;
    }

    public WageTableHistory(String companyId, String wageTableCode, List<YearMonthHistoryItem> history) {
        this.cid = cid;
        this.wageTableCode = new WageTableCode(wageTableCode);
        this.history = history;
    }
}
