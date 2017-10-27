package nts.uk.shr.infra.file.report.masterlist.generator;

import nts.arc.layer.infra.file.export.FileGeneratorContext;

public interface MasterListReportGenerator {

	void generate(FileGeneratorContext generatorContext, MasterListExportSource dataSource);
}
