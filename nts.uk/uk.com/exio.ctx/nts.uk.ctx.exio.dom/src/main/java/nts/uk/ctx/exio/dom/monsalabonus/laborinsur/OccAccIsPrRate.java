package nts.uk.ctx.exio.dom.monsalabonus.laborinsur;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

/**
* 労災保険料率
*/
@AllArgsConstructor
@Getter
public class OccAccIsPrRate extends AggregateRoot
{
    
    /**
    * 
    */
    private String ocAcIsPrRtId;
    
    /**
    * 
    */
    private String hisId;
    
    /**
    * 
    */
    private int occAccInsurBusNo;
    
    /**
    * 
    */
    private int fracClass;
    
    /**
    * 
    */
    private int empConRatio;
    
    
}
