package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;

import java.util.Optional;

/**
* 健保番号情報
*/
@Getter
@Setter
@NoArgsConstructor
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
        this.careInsurNumber = careIsNumber == null ? Optional.empty() : Optional.of(new NurCareInsurNum(careIsNumber));
        this.healInsNumber = healInsurNumber == null ? Optional.empty() : Optional.of(new HealInsurNumber(healInsurNumber));
    }

    public static HealInsurNumberInfor createFromJavaType(String historyId, String careInsurNumber, String healInsNumber){
        return new HealInsurNumberInfor(historyId, careInsurNumber, healInsNumber);
    }
}
