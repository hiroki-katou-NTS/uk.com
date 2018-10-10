package nts.uk.ctx.pr.core.app.find.wageprovision.individualwagecontract;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.SalIndAmount;

/**
* 給与個人別金額: DTO
*/
@AllArgsConstructor
@Value
public class SalIndAmountDto
{
    
    /**
    * 履歴ID
    */
    private String historyId;
    
    /**
    * 金額
    */
    private long amountOfMoney;
    
    
    public static SalIndAmountDto fromDomain(SalIndAmount domain)
    {
        return new SalIndAmountDto(domain.getHistoryId(), domain.getAmountOfMoney().v());
    }
    
}
