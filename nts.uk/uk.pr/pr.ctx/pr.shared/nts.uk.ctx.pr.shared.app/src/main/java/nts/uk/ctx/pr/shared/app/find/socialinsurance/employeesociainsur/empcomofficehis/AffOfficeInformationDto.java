package nts.uk.ctx.pr.shared.app.find.socialinsurance.employeesociainsur.empcomofficehis;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.AffOfficeInformation;

/**
* 所属事業所情報: DTO
*/
@AllArgsConstructor
@Value
public class AffOfficeInformationDto
{
    
    /**
    * 社会保険事業所コード
    */
    private String socialInsuranceCode;
    
    /**
    * 履歴ID
    */
    private String hisId;
    
    
    public static AffOfficeInformationDto fromDomain(AffOfficeInformation domain)
    {
        return null;
    }
    
}
