package nts.uk.ctx.sys.assist.app.find.salary;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.sys.assist.dom.salary.IncomTaxBaseYear;

/**
* 所得税基準年月日
*/
@AllArgsConstructor
@Value
public class IncomTaxBaseYearDto
{
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * 処理区分NO
    */
    private int processCateNo;
    
    /**
    * 基準日
    */
    private int refeDate;
    
    /**
    * 基準年
    */
    private int baseYear;
    
    /**
    * 基準月
    */
    private int baseMonth;
    
    
    public static IncomTaxBaseYearDto fromDomain(IncomTaxBaseYear domain)
    {
        return new IncomTaxBaseYearDto(domain.getCid(), domain.getProcessCateNo(), domain.getRefeDate().value, domain.getBaseYear().value, domain.getBaseMonth().value);
    }
    
}
