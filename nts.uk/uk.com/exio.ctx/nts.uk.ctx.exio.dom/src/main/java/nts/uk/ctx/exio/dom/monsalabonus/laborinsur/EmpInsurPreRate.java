package nts.uk.ctx.exio.dom.monsalabonus.laborinsur;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

/**
* 雇用保険料率
*/
@AllArgsConstructor
@Getter
public class EmpInsurPreRate extends AggregateRoot
{
    
    /**
    * 
    */
    private String hisId;
    
    /**
    * 
    */
    private String empPreRateId;
    
    /**
    * 
    */
    private String indBdRatio;
    
    /**
    * 
    */
    private String empContrRatio;
    
    /**
    * 
    */
    private int perFracClass;
    
    /**
    * 
    */
    private int busiOwFracClass;
    
    
}
