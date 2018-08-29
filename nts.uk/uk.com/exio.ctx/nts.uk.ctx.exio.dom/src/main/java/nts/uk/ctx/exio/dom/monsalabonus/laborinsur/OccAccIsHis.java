package nts.uk.ctx.exio.dom.monsalabonus.laborinsur;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

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
    * 履歴ID
    */
    private String hisId;
    
    /**
    * 年月期間
    */
    private int startDate;
    
    /**
    * 年月期間
    */
    private int endDate;
    
    
}
