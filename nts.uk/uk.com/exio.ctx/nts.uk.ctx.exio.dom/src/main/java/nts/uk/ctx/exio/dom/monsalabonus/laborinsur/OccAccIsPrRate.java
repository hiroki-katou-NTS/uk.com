package nts.uk.ctx.exio.dom.monsalabonus.laborinsur;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

import java.util.List;

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
    /*
    *各事業負担率
    */
    private List<OccAccInsurBusiBurdenRatio> eachBusBurdenRatio;

    public OccAccIsPrRate(String ocAcIsPrRtId, String hisId,List<OccAccInsurBusiBurdenRatio> eachBusBurdenRatio) {
        this.ocAcIsPrRtId = ocAcIsPrRtId;
        this.hisId = hisId;
        this.eachBusBurdenRatio=eachBusBurdenRatio;
    }
}
