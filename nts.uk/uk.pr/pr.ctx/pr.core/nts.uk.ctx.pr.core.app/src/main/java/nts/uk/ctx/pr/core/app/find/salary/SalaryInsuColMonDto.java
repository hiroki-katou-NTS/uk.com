package nts.uk.ctx.pr.core.app.find.salary;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.salary.SalaryInsuColMon;

/**
* 給与社会保険徴収月
*/
@AllArgsConstructor
@Value
public class SalaryInsuColMonDto
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
    * 徴収月
    */
    private int monthCollected;
    
    
    public static SalaryInsuColMonDto fromDomain(SalaryInsuColMon domain)
    {
        return new SalaryInsuColMonDto(domain.getProcessCateNo(), domain.getCid(), domain.getMonthCollected().value);
    }
    
}
