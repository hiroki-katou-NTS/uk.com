package nts.uk.ctx.pr.file.app.core.empinsqualifiinfo.empinsqualifinfo;

import lombok.Value;
import nts.arc.time.GeneralDate;

import java.util.List;

@Value
public class EmpInsGetQualifReportQuery {
    private EmpInsReportSettingExport empInsReportSetting;
    private EmpInsRptTxtSettingExport empInsRptTxtSetting;
    private GeneralDate fillingDate;
    private GeneralDate startDate;
    private GeneralDate endDate;
    private List<String> empIds;
}