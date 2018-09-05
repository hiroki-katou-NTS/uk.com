package nts.uk.ctx.sys.assist.app.find.salary;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.sys.assist.dom.salary.SociInsuStanDate;

/**
* 社会保険基準年月日
*/
@AllArgsConstructor
@Value
public class SociInsuStanDateDto
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
    * 基準月
    */
    private int baseMonth;
    
    /**
    * 基準年
    */
    private int baseYear;
    
    /**
    * 基準日
    */
    private int refeDate;
    
    
    public static SociInsuStanDateDto fromDomain(SociInsuStanDate domain)
    {
        return new SociInsuStanDateDto(domain.getCid(), domain.getProcessCateNo(), domain.getBaseMonth().value, domain.getBaseYear().value, domain.getRefeDate().value);
    }
    
}
