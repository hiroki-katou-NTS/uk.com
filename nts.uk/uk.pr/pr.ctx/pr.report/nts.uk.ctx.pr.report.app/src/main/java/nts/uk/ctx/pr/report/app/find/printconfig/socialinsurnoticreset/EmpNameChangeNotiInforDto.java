package nts.uk.ctx.pr.report.app.find.printconfig.socialinsurnoticreset;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.EmpNameChangeNotiInfor;

import javax.ejb.Stateless;

/**
* 社員氏名変更届情報: DTO
*/

@AllArgsConstructor
@Value
public class EmpNameChangeNotiInforDto
{
    
    /**
    * 社員ID
    */
    private String employeeId;
    
    /**
    * 会社ID
    */
    private String companyId;
    
    /**
    * 健康保険被保険者証不要
    */
    private int healInsurPersonNoNeed;
    
    /**
    * その他
    */
    private int other;
    
    /**
    * その他備考
    */
    private String otherRemarks;
    
    
    public static EmpNameChangeNotiInforDto fromDomain(EmpNameChangeNotiInfor domain)
    {
        return new EmpNameChangeNotiInforDto(domain.getEmployeeId(), domain.getCompanyId(), domain.getHealInsurPersonNoNeed(), domain.getOther(), domain.getOtherRemarks().map(i->i.v()).orElse(null));
    }
    
}
