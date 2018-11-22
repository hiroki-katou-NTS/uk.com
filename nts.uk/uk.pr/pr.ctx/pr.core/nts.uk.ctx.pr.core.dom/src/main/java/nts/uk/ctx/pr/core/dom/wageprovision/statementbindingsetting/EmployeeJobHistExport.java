package nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmployeeJobHistExport {
    private String employeeId;
    private String jobTitleID;
    private String jobTitleName;
    private GeneralDate startDate;
    private GeneralDate endDate;
}
