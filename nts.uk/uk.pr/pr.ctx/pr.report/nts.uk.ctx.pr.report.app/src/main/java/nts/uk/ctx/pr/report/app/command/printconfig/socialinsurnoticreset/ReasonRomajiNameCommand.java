package nts.uk.ctx.pr.report.app.command.printconfig.socialinsurnoticreset;

import lombok.Value;

@Value
public class ReasonRomajiNameCommand {
    private String empId;
    private String basicPenNumber;
    private EmpNameReportCommand empNameReportCommand;
    private int screenMode ;
}
