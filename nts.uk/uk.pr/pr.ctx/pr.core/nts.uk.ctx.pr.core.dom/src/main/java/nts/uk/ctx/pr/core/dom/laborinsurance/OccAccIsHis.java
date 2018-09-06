package nts.uk.ctx.pr.core.dom.laborinsurance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.history.strategic.ContinuousResidentHistory;

import java.util.List;

/**
* 労災保険履歴
*/
@AllArgsConstructor
@Getter
public class OccAccIsHis extends AggregateRoot implements ContinuousResidentHistory
{
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * 履歴
    */
    private List<YearMonthHistoryItem> history;


    @Override
    public List<YearMonthHistoryItem> items() {
        return this.history;
    }
}
