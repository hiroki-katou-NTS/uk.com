package nts.uk.ctx.exio.dom.monsalabonus.laborinsur;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

/**
* 労災保険事業
*/
@AllArgsConstructor
@Getter
public class OccAccInsurBus extends AggregateRoot
{
    
    /**
    * 
    */
    private String cid;
    
    /**
    * 
    */
    private int occAccInsurBusNo;
    
    /**
    * 
    */
    private int toUse;
    
    /**
    * 
    */
    private String name;
    
    
}
