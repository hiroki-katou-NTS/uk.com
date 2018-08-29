package nts.uk.ctx.exio.dom.monsalabonus.laborinsur;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

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
    * 個人負担率
    */
    private String indBdRatio;
    
    /**
    * 事業主負担率
    */
    private String empContrRatio;
    
    /**
    * 個人端数区分
    */
    private InsuPremiumFractionClassification perFracClass;
    
    /**
    * 事業主端数区分
    */
    private InsuPremiumFractionClassification busiOwFracClass;
    
    
}
