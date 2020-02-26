package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurassocinfor;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
* 健保組合情報
*/
@Getter
public class HealthCarePortInfor extends AggregateRoot {
    
    /**
    * 履歴ID
    */
    private String historyId;
    
    /**
    * 健康保険組合番号
    */
    private HealInsurAssNumber healInsurUnionNumber;
    
    public HealthCarePortInfor(String hisId, String healInsurUnionNmber) {
        this.historyId = hisId;
        this.healInsurUnionNumber = new HealInsurAssNumber(healInsurUnionNmber);
    }
    
}
