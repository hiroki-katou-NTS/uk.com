package nts.uk.ctx.pr.core.dom.wageprovision.formula;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTableCode;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.history.strategic.PersistentHistory;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import java.util.List;

/**
* 計算式履歴
*/
@Getter
public class FormulaHist extends AggregateRoot implements PersistentHistory<YearMonthHistoryItem, YearMonthPeriod,YearMonth> {

    /**
    * 会社ID
    */
    private String cid;

    /**
     * 計算式コード
     */
    private FormulaCode formulaCode;
    /**
     * 履歴
     */
    private List<YearMonthHistoryItem> history;

    public FormulaHist(String cid, String formulaCode, List<YearMonthHistoryItem> history) {
        this.formulaCode = new FormulaCode(formulaCode);
        this.cid = cid;
        this.history = history;
    }

    @Override
    public List<YearMonthHistoryItem> items() {
        return history;
    }
}
