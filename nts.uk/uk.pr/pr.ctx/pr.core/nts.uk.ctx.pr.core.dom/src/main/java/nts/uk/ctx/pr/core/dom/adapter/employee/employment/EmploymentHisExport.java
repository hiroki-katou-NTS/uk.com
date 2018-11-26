package nts.uk.ctx.pr.core.dom.adapter.employee.employment;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.EmploymentCodeAndPeriod;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmploymentHisExport {
    private String employeeId;
    public List<EmploymentCodeAndPeriod> lstEmpCodeandPeriod;
}
