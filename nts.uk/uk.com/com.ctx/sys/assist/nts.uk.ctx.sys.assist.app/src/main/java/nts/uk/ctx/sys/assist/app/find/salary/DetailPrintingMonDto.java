package nts.uk.ctx.sys.assist.app.find.salary;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.sys.assist.dom.salary.DetailPrintingMon;

/**
* 明細印字月
*/
@AllArgsConstructor
@Value
public class DetailPrintingMonDto
{
    
    /**
    * 処理区分NO
    */
    private int processCateNo;
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * 印字月
    */
    private int printingMonth;
    
    
    public static DetailPrintingMonDto fromDomain(DetailPrintingMon domain)
    {
        return new DetailPrintingMonDto(domain.getProcessCateNo(), domain.getCid(), domain.getPrintingMonth().value);
    }
    
}
