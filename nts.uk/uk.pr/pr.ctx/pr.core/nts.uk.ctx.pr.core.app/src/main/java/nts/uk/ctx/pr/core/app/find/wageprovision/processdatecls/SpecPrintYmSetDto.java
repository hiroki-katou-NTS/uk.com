package nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.SpecPrintYmSet;

/**
* 明細書印字年月設定
*/
@AllArgsConstructor
@Value
public class SpecPrintYmSetDto
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
    
    
    public static SpecPrintYmSetDto fromDomain(SpecPrintYmSet domain)
    {
        return new SpecPrintYmSetDto(domain.getCid(), domain.getProcessCateNo(), domain.getProcessDate(), domain.getPrintDate());
    }
    
}
