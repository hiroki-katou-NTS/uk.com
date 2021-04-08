package nts.uk.ctx.at.shared.dom.adapter.employee;

import lombok.Getter;

@Getter
public class EmployeeBasicInfoImport {
    private String sid;
    private String employeeCode;
    private String employeeName;

    public EmployeeBasicInfoImport(String sid, String employeeCode, String employeeName) {
        this.sid = sid;
        this.employeeCode = employeeCode;
        this.employeeName = employeeName;
    }
}
