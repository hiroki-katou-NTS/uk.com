package nts.uk.ctx.pr.core.app.command.wageprovision.processdatecls;

import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.SpecPrintYmSet;
import nts.uk.shr.com.context.AppContexts;

@Value
public class SpecPrintYmSetCommand
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
    * PROCESS_DATE
    */
    private int processDate;
    
    /**
    * PRINT_DATE
    */
    private int printDate;
    
    SpecPrintYmSet fromCommandToDomain(){
        String cid = AppContexts.user().companyId();
        return new SpecPrintYmSet(cid, processCateNo, this.processDate, this.printDate);
    }

}
