package nts.uk.ctx.at.function.dom.outputitemsofworkstatustable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;



@Getter
@Setter
@AllArgsConstructor
public class EmployeeIdAndPeriod {
    public String employeeId;
    public GeneralDate date;
}
