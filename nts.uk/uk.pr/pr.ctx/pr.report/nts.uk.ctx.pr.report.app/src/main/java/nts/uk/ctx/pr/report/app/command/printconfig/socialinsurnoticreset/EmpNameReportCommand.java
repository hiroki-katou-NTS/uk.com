package nts.uk.ctx.pr.report.app.command.printconfig.socialinsurnoticreset;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.EmpNameReport;
import nts.uk.shr.com.context.AppContexts;

@Value
@AllArgsConstructor
public class EmpNameReportCommand {
    private String empId;
    private NameNotificationSetCommand personalSet;
    private NameNotificationSetCommand spouse;

    public EmpNameReport toDomain(){
        return new EmpNameReport(empId, AppContexts.user().companyId(), personalSet.toDomains(), spouse.toDomains());
    }

}
