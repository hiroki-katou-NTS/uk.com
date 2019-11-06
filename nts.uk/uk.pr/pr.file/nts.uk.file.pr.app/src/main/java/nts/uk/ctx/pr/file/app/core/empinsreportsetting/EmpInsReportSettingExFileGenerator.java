package nts.uk.ctx.pr.file.app.core.empinsreportsetting;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.pr.file.app.core.insurenamechangenoti.InsuredNameChangedNotiExportData;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.SocialInsurNotiCreateSet;

import java.util.List;

public interface EmpInsReportSettingExFileGenerator {
    void generate(FileGeneratorContext fileContext, List<EmpInsReportSettingExportData> data);

}
