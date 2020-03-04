package nts.uk.ctx.pr.file.app.core.empinsqualifiinfo.empinsofficeinfo;

import lombok.Data;
import nts.arc.time.GeneralDate;

import java.util.List;

@Data
public class NotifiOfChangInNameInsPerExportQuery {
    private NotifiOfChangInNameInsPerExport empInsReportSettingExport;
    private List<EmployeeChangeDate> empIdChangDate;
    private GeneralDate fillingDate;
}
