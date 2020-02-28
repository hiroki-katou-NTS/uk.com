package nts.uk.ctx.pr.report.app.find.printconfig.empinsreportsetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.adapter.employee.employee.EmployeeInfoEx;
import nts.uk.ctx.pr.core.dom.adapter.person.PersonExport;
import nts.uk.ctx.pr.core.dom.adapter.person.PersonNameGroupExport;

import java.util.List;
import java.util.Optional;

@Value
@AllArgsConstructor
public class PersonDto {

    /** The person id - 個人ID */
    private String personId;

    private String oldName;

    private String employeeId;

}
