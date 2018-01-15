package approve.employee;

import nts.arc.layer.infra.file.export.FileGeneratorContext;

public interface EmployeeApproverRootOutputGenerator {
	void generate(FileGeneratorContext generatorContext, EmployeeApproverDataSource dataSource);

}
