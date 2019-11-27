package nts.uk.ctx.pr.core.dom.laborinsurance;
/*
* 労災保険各事業負担率
*
* */

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class OccAccInsurBusiBurdenRatio {

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
    private InsuranceRate empConRatio;

}
