package nts.uk.ctx.pr.report.app.command.printconfig.socialinsurnoticreset;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.EmpNameReport;

@Value
@AllArgsConstructor
public class EmpNameReportCommand {
    private String empId;
    private NameNotificationSetCommand spouse;
    private NameNotificationSetCommand personalSet;

    public EmpNameReport toDomain(){
        return new EmpNameReport(empId, spouse.toDomains(), personalSet.toDomains());
    }

}
