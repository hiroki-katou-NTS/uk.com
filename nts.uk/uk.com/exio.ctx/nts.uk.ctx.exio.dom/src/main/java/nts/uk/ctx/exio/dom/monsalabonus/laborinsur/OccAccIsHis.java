package nts.uk.ctx.exio.dom.monsalabonus.laborinsur;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

/**
* 労災保険履歴
*/
@AllArgsConstructor
@Getter
public class OccAccIsHis extends AggregateRoot
{
    
    /**
    * 
    */
    private String cid;
    
    /**
    * 
    */
    private String hisId;
    
    /**
    * 
    */
    private int startDate;
    
    /**
    * 
    */
    private int endDate;
    
    
}
