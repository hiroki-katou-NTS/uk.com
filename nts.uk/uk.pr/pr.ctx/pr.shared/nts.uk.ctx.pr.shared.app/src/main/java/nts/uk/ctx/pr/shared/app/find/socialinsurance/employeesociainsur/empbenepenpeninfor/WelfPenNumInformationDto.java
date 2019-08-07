package nts.uk.ctx.pr.shared.app.find.socialinsurance.employeesociainsur.empbenepenpeninfor;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.WelfPenNumInformation;

/**
* 厚生年金番号情報: DTO
*/
@AllArgsConstructor
@Value
public class WelfPenNumInformationDto
{
    
    /**
    * 厚年得喪期間履歴ID
    */
    private String historyId;
    
    /**
    * 坑内員区分
    */
    private int undergroundDivision;
    
    /**
    * 厚生年金番号
    */
    private String welPenNumber;
    
    
    public static WelfPenNumInformationDto fromDomain(WelfPenNumInformation domain)
    {
        return null;
    }
    
}
