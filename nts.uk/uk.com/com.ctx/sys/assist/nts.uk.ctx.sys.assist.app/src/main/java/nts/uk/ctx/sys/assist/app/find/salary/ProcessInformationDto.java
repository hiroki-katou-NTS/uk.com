package nts.uk.ctx.sys.assist.app.find.salary;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.sys.assist.dom.salary.ProcessInformation;

/**
* 処理区分基本情報
*/
@AllArgsConstructor
@Value
public class ProcessInformationDto
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
    * DEPRECAT_CATE
    */
    private int deprecatCate;
    
    /**
    * PROCESS_DIVISION_NAME
    */
    private String processDivisionName;
    
    
    public static ProcessInformationDto fromDomain(ProcessInformation domain)
    {
        return new ProcessInformationDto(domain.getCid(), domain.getProcessCateNo(), domain.getDeprecatCate().value, domain.getProcessDivisionName().v());
    }
    
}
