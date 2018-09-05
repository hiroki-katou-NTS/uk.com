package nts.uk.ctx.sys.assist.app.find.salary;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.sys.assist.dom.salary.CloseDate;

/**
* 勤怠締め年月日
*/
@AllArgsConstructor
@Value
public class CloseDateDto
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
    * 勤怠締め日
    */
    private int timeCloseDate;
    
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
    
    
    public static CloseDateDto fromDomain(CloseDate domain)
    {
        return new CloseDateDto(domain.getProcessCateNo(), domain.getCid(), domain.getTimeCloseDate(), domain.getBaseMonth().get().value, domain.getBaseYear().get().value, domain.getRefeDate().get().value);
    }
    
}
