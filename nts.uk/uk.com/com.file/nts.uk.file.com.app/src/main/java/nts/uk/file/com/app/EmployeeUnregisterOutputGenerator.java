package nts.uk.file.com.app;

import nts.arc.layer.infra.file.export.FileGeneratorContext;

public interface EmployeeUnregisterOutputGenerator {
	void generate(FileGeneratorContext generatorContext, EmployeeUnregisterOutputDataSoure dataSource);
}
