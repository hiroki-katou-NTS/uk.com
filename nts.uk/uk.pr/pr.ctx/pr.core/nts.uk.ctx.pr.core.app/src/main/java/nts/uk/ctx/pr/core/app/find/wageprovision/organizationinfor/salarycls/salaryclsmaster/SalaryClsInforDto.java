package nts.uk.ctx.pr.core.app.find.wageprovision.organizationinfor.salarycls.salaryclsmaster;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.organizationinfor.salarycls.salaryclsmaster.SalaryClassificationInformation;

/**
* 給与分類情報: DTO
*/
@AllArgsConstructor
@Value
public class SalaryClsInforDto
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
    
    
    public static SalaryClsInforDto fromDomain(SalaryClassificationInformation domain)
    {
        return new SalaryClsInforDto(domain.getCompanyId(), domain.getSalaryClassificationCode().v(), domain.getSalaryClassificationName().v(), domain.getMemo().map(i->i.v()).orElse(null));
    }
    
}
