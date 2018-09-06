package nts.uk.ctx.exio.dom.monsalabonus.laborinsur;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.shr.com.history.YearMonthHistoryItem;

import java.util.List;

/**
* 労災保険履歴
*/
@AllArgsConstructor
@Getter
public class OccAccIsHis extends AggregateRoot
{
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * 履歴
    */
    private List<YearMonthHistoryItem> history;
    

    
    
}
