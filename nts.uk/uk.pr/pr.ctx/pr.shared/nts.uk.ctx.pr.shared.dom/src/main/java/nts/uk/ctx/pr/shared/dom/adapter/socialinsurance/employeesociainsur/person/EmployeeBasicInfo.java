package nts.uk.ctx.pr.shared.dom.adapter.socialinsurance.employeesociainsur.person;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;

@Value
@AllArgsConstructor
public class EmployeeBasicInfo {
    private String pid;

    private String businessName;

    private GeneralDate entryDate;

    private int gender;

    private GeneralDate birthDay;

    /** The employee id. */
    private String employeeId;

    /** The employee code. */
    private String employeeCode;

    private GeneralDate retiredDate;
}
