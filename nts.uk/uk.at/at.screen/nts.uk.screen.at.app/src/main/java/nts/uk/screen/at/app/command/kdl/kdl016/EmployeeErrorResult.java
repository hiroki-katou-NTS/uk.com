package nts.uk.screen.at.app.command.kdl.kdl016;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class EmployeeErrorResult {
    private String employeeCode;
    private String employeeName;
    private String startDate;
    private String endDate;
    private String errorMessage;
}
