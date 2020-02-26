package nts.uk.file.pr.app.report.printconfig.empinsreportsetting;

import nts.arc.layer.infra.file.export.FileGeneratorContext;

public interface EmpInsReportTxtSettingCsvGenerator {
	public void generate(FileGeneratorContext generatorContext, EmpInsReportSettingExportData dataSource);
}
