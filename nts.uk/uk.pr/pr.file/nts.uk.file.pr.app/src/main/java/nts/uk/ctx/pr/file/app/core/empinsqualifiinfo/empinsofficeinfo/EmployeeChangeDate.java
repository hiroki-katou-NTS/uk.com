package nts.uk.ctx.pr.file.app.core.empinsqualifiinfo.empinsofficeinfo;

import lombok.Value;

@Value
public class EmployeeChangeDate {
    /**
     * 会社ID
     */
    private String employeeId;

    /**
     * ユーザID
     */
    private String changeDate;
}
