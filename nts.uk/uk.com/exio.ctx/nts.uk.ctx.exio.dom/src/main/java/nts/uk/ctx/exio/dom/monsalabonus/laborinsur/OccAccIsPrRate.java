package nts.uk.ctx.exio.dom.monsalabonus.laborinsur;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;

/**
* 労災保険料率
*/

@Getter
public class OccAccIsPrRate extends AggregateRoot
{
    
    /**
    * 
    */
    private String ocAcIsPrRtId;
    
    /**
    * 履歴ID
    */
    private String hisId;
    
    /**
    * 労災保険事業No
    */
    private int occAccInsurBusNo;
    
    /**
    * 端数区分
    */
    private InsuPremiumFractionClassification fracClass;
    
    /**
    * 事業主負担率
    */
    private String empConRatio;

    public OccAccIsPrRate(String ocAcIsPrRtId, String hisId, int occAccInsurBusNo, int fracClass, String empConRatio) {
        this.ocAcIsPrRtId = ocAcIsPrRtId;
        this.hisId = hisId;
        this.occAccInsurBusNo = occAccInsurBusNo;
        this.fracClass = EnumAdaptor.valueOf(fracClass, InsuPremiumFractionClassification.class);
        this.empConRatio = empConRatio;
    }
}
