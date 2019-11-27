package nts.uk.ctx.pr.shared.app.find.socialinsurance.employeesociainsur.emphealinsurbeneinfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurbeneinfo.EmpBasicPenNumInfor;

/**
* 社員基礎年金番号情報: DTO
*/
@AllArgsConstructor
@Value
@Data
public class EmpBasicPenNumInforDto {
    
    /**
    * 社員ID
    */
    private String employeeId;
    
    /**
    * 基礎年金番号
    */
    private String basicPenNumber;
    
    
    public static EmpBasicPenNumInforDto fromDomain(EmpBasicPenNumInfor domain)
    {
        return new EmpBasicPenNumInforDto(domain.getEmployeeId(), domain.getBasicPenNumber().map(i->i.v()).orElse(null));
    }
    
}
