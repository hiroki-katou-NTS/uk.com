package nts.uk.ctx.at.record.app.command.monthly.agreement.monthlyresult.specialprovision;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.adapter.personempbasic.PersonEmpBasicInfoDto;

import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
@Setter
public class ErrorResultDto {
    private String employeeId;
    private String employeeCode;
    private String employeeName;
    private List<ExcessErrorContentDto> errors;

    public void mappingEmpInfo(Map<String, PersonEmpBasicInfoDto> empInfos){
        if (empInfos.containsKey(this.employeeId)){
            PersonEmpBasicInfoDto emp = empInfos.get(employeeId);
            this.setEmployeeCode(emp.getEmployeeCode());
            this.setEmployeeName(emp.getBusinessName());
        }
    }
}
