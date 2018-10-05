package nts.uk.ctx.pr.core.dom.laborinsurance;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
* 雇用保険料率
*/

@Getter
public class EmpInsurPreRate extends AggregateRoot
{
    
    /**
    * 履歴ID
    */
    private String hisId;
    /**
     *農林水産清酒製造の事業負担率
     */
    private EmpInsurBusBurRatio busRatioOfAgriForestFish;
    /**
     *建設の事業負担率
     */
    private EmpInsurBusBurRatio busBurRatioOfConstruction;
    /**
     *一般の事業負担率
     */
    private EmpInsurBusBurRatio genBusBurdenRatio;


    public EmpInsurPreRate(String hisId, EmpInsurBusBurRatio busRatioOfAgriForestFish, EmpInsurBusBurRatio busBurRatioOfConstruction, EmpInsurBusBurRatio genBusBurdenRatio) {
        this.hisId = hisId;
        this.busRatioOfAgriForestFish = busRatioOfAgriForestFish;
        this.busBurRatioOfConstruction = busBurRatioOfConstruction;
        this.genBusBurdenRatio = genBusBurdenRatio;
    }
}
