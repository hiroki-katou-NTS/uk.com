package nts.uk.file.at.app.export.worktime;

import nts.arc.layer.infra.file.export.FileGeneratorContext;

public interface WorkTimeReportGenerator {
	
	void generate(FileGeneratorContext generatorContext, WorkTimeReportDatasource dataSource);
}
