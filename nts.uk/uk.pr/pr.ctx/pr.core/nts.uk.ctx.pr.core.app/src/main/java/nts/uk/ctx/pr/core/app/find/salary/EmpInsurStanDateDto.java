package nts.uk.ctx.pr.core.app.find.salary;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.salary.EmpInsurStanDate;

/**
* 雇用保険基準日
*/
@AllArgsConstructor
@Value
public class EmpInsurStanDateDto
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
    * 基準月
    */
    private int baseMonth;
    
    
    public static EmpInsurStanDateDto fromDomain(EmpInsurStanDate domain)
    {
        return new EmpInsurStanDateDto(domain.getCid(), domain.getProcessCateNo(), domain.getRefeDate().value, domain.getBaseMonth().value);
    }
    
}
