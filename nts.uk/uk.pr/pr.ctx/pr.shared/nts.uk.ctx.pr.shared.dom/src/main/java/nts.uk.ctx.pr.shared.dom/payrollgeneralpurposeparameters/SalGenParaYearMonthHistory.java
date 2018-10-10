package nts.uk.ctx.pr.shared.dom.payrollgeneralpurposeparameters;

import java.util.List;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.history.strategic.ContinuousResidentHistory;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

/**
* 給与汎用パラメータ年月履歴
*/
@Getter
public class SalGenParaYearMonthHistory extends AggregateRoot implements ContinuousResidentHistory<YearMonthHistoryItem, YearMonthPeriod,YearMonth> {
    
    /**
    * パラメータNo
    */
    private String paraNo;
    
    /**
    * 会社ID
    */
    private String cID;
    
    /**
    * 履歴
    */
    private List<YearMonthHistoryItem> history;
    
    public SalGenParaYearMonthHistory(String paraNo, String cid, List<YearMonthHistoryItem> history ) {
        this.cID =cid ;
        this.paraNo =paraNo ;
        this.history = history;
    }

    @Override
    public List<YearMonthHistoryItem> items() {
        return this.history;
    }
}
