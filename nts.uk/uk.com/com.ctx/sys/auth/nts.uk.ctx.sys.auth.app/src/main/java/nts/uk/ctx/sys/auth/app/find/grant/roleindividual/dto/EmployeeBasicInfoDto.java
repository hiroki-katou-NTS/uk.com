package nts.uk.ctx.sys.auth.app.find.grant.roleindividual.dto;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.auth.dom.adapter.person.EmployeeBasicInforAuthImport;

@Value
public class EmployeeBasicInfoDto {
    //個人ID
    public String personId;

    //社員ID
    public String employeeId;

    //ビジネスネーム
    public String businessName;

    //性別
    public int gender;

    //生年月日
    public GeneralDate birthday;

    //社員コード
    public String employeeCode;

    public static EmployeeBasicInfoDto fromDomain(EmployeeBasicInforAuthImport domain) {
        return new EmployeeBasicInfoDto(domain.getPid(), domain.getEmployeeId(), domain.getBusinessName(), domain.getGender(),
                domain.getBirthDay(), domain.getEmployeeCode());
    }
}
