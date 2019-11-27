package nts.uk.ctx.pr.shared.app.find.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.MultiEmpWorkInfo;

/**
* 社員二以上事業所勤務情報: DTO
*/
@AllArgsConstructor
@Value
@Data
public class MultiEmpWorkInfoDto {
    
    /**
    * 社員ID
    */
    private String empId;
    
    /**
    * 二以上事業所勤務者
    */
    private int isMoreEmp;
    
    
    public static MultiEmpWorkInfoDto fromDomain(MultiEmpWorkInfo domain)
    {
        return new MultiEmpWorkInfoDto(domain.getEmpId(), domain.getIsMoreEmp());
    }
    
}
