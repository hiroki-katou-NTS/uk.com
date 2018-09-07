package nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.DetailPrintingMonth;

/**
* 明細印字月
*/
@AllArgsConstructor
@Value
public class DetailPrintingMonDto
{
    
    
    /**
    * 印字月
    */
    private int printingMonth;
    
    
    public static DetailPrintingMonDto fromDomain(DetailPrintingMonth domain)
    {
        return new DetailPrintingMonDto( domain.getPrintingMonth().value);
    }
    
}
