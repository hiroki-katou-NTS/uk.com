package nts.uk.screen.at.app.command.kdl.kdl016;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class EmployeeErrorResult {
    private String employeeCode;
    private String employeeName;
    private String startDate;
    private String endDate;
    private String errorMessage;

    private String employeeDisplay;
    private String periodDisplay;

    public EmployeeErrorResult(String employeeCode, String employeeName, String startDate, String endDate, String errorMessage) {
        this.employeeCode = employeeCode;
        this.employeeName = employeeName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.errorMessage = errorMessage;
        this.employeeDisplay = employeeCode + "　" + employeeName;
        this.periodDisplay = StringUtils.isNotEmpty(endDate) ? startDate + "～" + endDate : startDate;
    }
}
