package nts.uk.ctx.pr.file.app.core.empinsreportsetting;

import lombok.Data;
import nts.arc.time.GeneralDate;

import java.util.List;

@Data
public class EmpInsReportSettingExportQuery {
    private EmpInsReportSettingExport empInsReportSettingExport;
    private List<EmployeeChangeDate> empIdChangDate;
    private GeneralDate fillingDate;
}
