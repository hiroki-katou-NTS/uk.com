package nts.uk.ctx.pr.shared.app.find.socialinsurance.employeesociainsur.empbenepenpeninfor;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.WelfarePenTypeInfor;

/**
* 厚生年金種別情報: DTO
*/
@AllArgsConstructor
@Value
public class WelfarePenTypeInforDto
{
    
    /**
    * 厚年得喪期間履歴ID
    */
    private String historyId;
    
    /**
    * 坑内員区分
    */
    private int undergroundDivision;
    
    
    public static WelfarePenTypeInforDto fromDomain(WelfarePenTypeInfor domain)
    {
        return null;
    }
    
}
