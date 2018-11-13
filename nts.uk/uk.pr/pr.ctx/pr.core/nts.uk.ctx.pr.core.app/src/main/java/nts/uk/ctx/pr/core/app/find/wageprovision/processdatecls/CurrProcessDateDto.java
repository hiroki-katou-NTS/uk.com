package nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.CurrProcessDate;

import java.util.Objects;

/**
* 現在処理年月
*/
@AllArgsConstructor
@Value
public class CurrProcessDateDto
{
    
    /**
    * CID
    */
    private String cid;
    
    /**
    * PROCESS_CATE_NO
    */
    private int processCateNo;
    
    /**
    * GIVE_CURR_TREAT_YEAR
    */
    private int giveCurrTreatYear;
    
    
    public static CurrProcessDateDto fromDomain(CurrProcessDate domain)
    {
        if(Objects.isNull(domain))
            return null;
        return new CurrProcessDateDto(domain.getCid(), domain.getProcessCateNo(), domain.getGiveCurrTreatYear().v());
    }
    
}
