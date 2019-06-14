package nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.EmpTiedProYear;

import java.util.List;
import java.util.stream.Collectors;

/**
* 処理年月に紐づく雇用
*/
@AllArgsConstructor
@Value
public class EmpTiedProYearDto
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
    * EMPLOYMENT_CODE
    */
    private List<String> getEmploymentCodes;
    
    
    public static EmpTiedProYearDto fromDomain(EmpTiedProYear domain)
    {

        return new EmpTiedProYearDto(domain.getCid(), domain.getProcessCateNo(),domain.getEmploymentCodes().stream().map(item -> item.v()).collect(Collectors.toList()));
    }



    
}
