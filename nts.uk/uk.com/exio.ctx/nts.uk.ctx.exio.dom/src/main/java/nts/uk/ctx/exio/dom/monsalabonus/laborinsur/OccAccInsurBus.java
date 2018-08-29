package nts.uk.ctx.exio.dom.monsalabonus.laborinsur;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
* 労災保険事業
*/
@AllArgsConstructor
@Getter
public class OccAccInsurBus extends AggregateRoot
{
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * 労災保険事業No
    */
    private int occAccInsurBusNo;
    
    /**
    * 利用する
    */
    private int toUse;
    
    /**
    * 名称
    */
    private String name;
    
    
}
