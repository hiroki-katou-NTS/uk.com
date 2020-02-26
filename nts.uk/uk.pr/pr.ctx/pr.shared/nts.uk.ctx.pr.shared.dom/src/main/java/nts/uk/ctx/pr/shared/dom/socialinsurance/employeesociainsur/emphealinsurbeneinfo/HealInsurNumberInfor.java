package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

import java.util.Optional;

/**
* 健保番号情報
*/
@Getter
public class HealInsurNumberInfor extends AggregateRoot {
    
    /**
    * 健保得喪期間履歴ID
    */
    private String historyId;
    
    /**
    * 介護保険番号
    */
    private Optional<NurCareInsurNum> careInsurNumber;
    
    /**
    * 健康保険番号
    */
    private Optional<HealInsurNumber> healInsNumber;
    
    public HealInsurNumberInfor(String historyId, String careIsNumber, String healInsurNumber) {
        this.historyId = historyId;
        this.healInsNumber = healInsurNumber == null ? Optional.empty() : Optional.of(new HealInsurNumber(healInsurNumber));
        this.careInsurNumber = careIsNumber == null ? Optional.empty() : Optional.of(new NurCareInsurNum(careIsNumber));
    }
    
}
