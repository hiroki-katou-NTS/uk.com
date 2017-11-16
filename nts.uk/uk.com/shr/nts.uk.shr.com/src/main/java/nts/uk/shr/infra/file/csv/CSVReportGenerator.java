package nts.uk.shr.infra.file.csv;

import nts.arc.layer.infra.file.export.FileGeneratorContext;

public interface CSVReportGenerator {

	void generate(FileGeneratorContext generatorContext, CSVFileData dataSource);
}
