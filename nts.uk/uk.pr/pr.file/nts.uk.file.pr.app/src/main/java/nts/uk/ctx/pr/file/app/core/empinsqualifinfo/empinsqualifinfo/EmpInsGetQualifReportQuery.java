package nts.uk.ctx.pr.file.app.core.empinsqualifinfo.empinsqualifinfo;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.file.app.core.empinsreportsetting.EmpInsReportSettingExport;

@Value
public class EmpInsGetQualifReportQuery {
    private EmpInsReportSettingExport empInsReportSettingExport;
    private EmpInsRptTxtSettingExport empInsRptTxtSettingExport;
    private GeneralDate fillingDate;
}


