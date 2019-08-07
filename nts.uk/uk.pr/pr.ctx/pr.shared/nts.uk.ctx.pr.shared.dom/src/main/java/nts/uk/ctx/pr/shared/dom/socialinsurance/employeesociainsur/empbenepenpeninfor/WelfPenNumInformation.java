package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;

import java.util.Optional;

/**
* 厚生年金番号情報
*/
@Getter
public class WelfPenNumInformation extends AggregateRoot {
    
    /**
    * 厚年得喪期間履歴ID
    */
    private String historyId;
    
    /**
    * 厚生年金番号
    */
    private Optional<EmpPensionNumber> welPenNumber;
    
    /**
    * 健保同一区分
    */
    private HealInsurSameCtg healInsSameCtg;
    
    public WelfPenNumInformation(String affMourPeriodHisid, int healInsurSameCtg, String welPenNumber) {
        this.historyId = affMourPeriodHisid;
        this.healInsSameCtg = EnumAdaptor.valueOf(healInsurSameCtg, HealInsurSameCtg.class);
        this.welPenNumber = welPenNumber == null ? Optional.empty() : Optional.of(new EmpPensionNumber(welPenNumber));
    }
    
}
