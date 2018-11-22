package nts.uk.ctx.pr.core.app.find.wageprovision.organizationinformation.salaryclassification.salaryclassificationmaster;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.pr.core.dom.wageprovision.organizationinformation.salaryclassification.salaryclassificationmaster.SalaryClassificationInformation;

/**
* 給与分類情報: DTO
*/
@AllArgsConstructor
@Value
public class SalaryClassificationInformationDto
{
    
    /**
    * 会社ID
    */
    private String companyId;
    
    /**
    * 給与分類コード
    */
    private String salaryClassificationCode;
    
    /**
    * 給与分類名称
    */
    private String salaryClassificationName;
    
    /**
    * メモ
    */
    private String memo;
    
    
    public static SalaryClassificationInformationDto fromDomain(SalaryClassificationInformation domain)
    {
        return new SalaryClassificationInformationDto(domain.getCompanyId(), domain.getSalaryClassificationCode().v(), domain.getSalaryClassificationName().v(), domain.getMemo().map(i->i.v()).orElse(null));
    }
    
}
