package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empfunmeminfor;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
* 基金加入員情報
*/
@Getter
public class FundMembership extends AggregateRoot {
    
    /**
    * 履歴ID
    */
    private String historyId;
    
    /**
    * 基金加入員番号
    */
    private FundMembershipNumber membersNumber;
    
    public FundMembership(String historyId, String membersNumber) {
        this.historyId = historyId;
        this.membersNumber = new FundMembershipNumber(membersNumber);
    }
    
}
