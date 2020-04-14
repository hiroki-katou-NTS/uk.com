package nts.uk.file.pr.app.report.printconfig.empinsreportsetting;

import nts.arc.layer.infra.file.export.FileGeneratorContext;

public interface EmpInsLossInfoCsvGenerator {
	public void generate(FileGeneratorContext generatorContext, EmpInsLossInfoExportData dataSource);
}
