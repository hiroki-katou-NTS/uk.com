package nts.uk.ctx.exio.dom.monsalabonus.laborinsur;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;

/**
* 雇用保険料率
*/

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

    public EmpInsurPreRate(String hisId, String empPreRateId, String indBdRatio, String empContrRatio, int perFracClass, int busiOwFracClass) {
        this.hisId = hisId;
        this.empPreRateId = empPreRateId;
        this.indBdRatio = indBdRatio;
        this.empContrRatio = empContrRatio;
        this.perFracClass = EnumAdaptor.valueOf(perFracClass, InsuPremiumFractionClassification.class);
        this.busiOwFracClass = EnumAdaptor.valueOf(busiOwFracClass, InsuPremiumFractionClassification.class);
    }
}
