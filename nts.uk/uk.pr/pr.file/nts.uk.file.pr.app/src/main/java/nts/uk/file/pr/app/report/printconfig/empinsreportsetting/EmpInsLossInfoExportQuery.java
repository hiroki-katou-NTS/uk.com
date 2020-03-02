package nts.uk.file.pr.app.report.printconfig.empinsreportsetting;

import java.util.List;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.report.app.command.printconfig.empinsreportsetting.EmpInsRptSettingCommand;
import nts.uk.ctx.pr.report.app.command.printconfig.empinsreportsetting.EmpInsRptTxtSettingCommand;

@Data
public class EmpInsLossInfoExportQuery {
	private List<String> employeeIds;
    private GeneralDate startDate;
    private GeneralDate endDate;
    private GeneralDate fillingDate;
    
    private EmpInsRptSettingCommand empInsReportSettingCommand;
    private EmpInsRptTxtSettingCommand empInsReportTxtSettingCommand;
}
