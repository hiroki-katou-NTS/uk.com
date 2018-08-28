package nts.uk.ctx.exio.dom.monsalabonus.laborinsur;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;


/**
* 労災保険履歴
*/
@AllArgsConstructor
@Getter
public class EmpInsurHis extends AggregateRoot
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
