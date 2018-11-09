package nts.uk.ctx.pr.core.dom.laborinsurance;

import lombok.AllArgsConstructor;
/**
 *雇用保険料率ID
 */
@AllArgsConstructor

public enum EmpInsurRateId {


    // 1:建設の事業負担率
    BUS_BUR_RATIO_OF_CONSTRUCTION(0),

    // 0:農林水産清酒製造の事業負担率
    BUS_RATIO_OF_AGRI_FOREST_FISH(1),

    // 2:一般の事業負担率
    GEN_BUS_BURDEN_RATIO(2);

    public final int value;
}