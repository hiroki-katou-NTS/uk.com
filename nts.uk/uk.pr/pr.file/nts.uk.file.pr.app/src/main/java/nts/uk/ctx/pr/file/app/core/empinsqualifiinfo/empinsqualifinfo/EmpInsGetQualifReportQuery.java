package nts.uk.ctx.pr.file.app.core.empinsqualifiinfo.empinsqualifinfo;

import lombok.Value;
import nts.arc.time.GeneralDate;

@Value
public class EmpInsGetQualifReportQuery {
    private EmpInsReportSettingExport empInsReportSettingExport;
    private EmpInsRptTxtSettingExport empInsRptTxtSettingExport;
    private GeneralDate fillingDate;
}


