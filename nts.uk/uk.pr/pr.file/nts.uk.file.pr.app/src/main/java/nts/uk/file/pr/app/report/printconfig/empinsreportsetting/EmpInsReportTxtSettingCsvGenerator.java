package nts.uk.file.pr.app.report.printconfig.empinsreportsetting;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.ExportDataCsv;

public interface EmpInsReportTxtSettingCsvGenerator {
	public void generate(FileGeneratorContext generatorContext, ExportDataCsv dataSource);
}
