package nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract;

import java.util.Optional;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.enums.EnumAdaptor;

/**
* 給与個人別金額
*/
@Getter
public class SalIndAmount extends AggregateRoot {
    
    /**
    * 履歴ID
    */
    private String historyId;
    
    /**
    * 金額
    */
    private AmountOfMoney amountOfMoney;
    
    public SalIndAmount(String historyId, long amountOfMoney) {
        this.historyId = historyId;
        this.amountOfMoney = new AmountOfMoney(amountOfMoney);
    }
    
}
