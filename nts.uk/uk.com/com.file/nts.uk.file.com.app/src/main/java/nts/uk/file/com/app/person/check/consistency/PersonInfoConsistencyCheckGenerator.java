package nts.uk.file.com.app.person.check.consistency;

import java.util.List;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.file.com.app.person.check.consistency.datasource.EmployeInfoErrorDataSource;

public interface PersonInfoConsistencyCheckGenerator {
	void generate(FileGeneratorContext generatorContext, List<EmployeInfoErrorDataSource> dataSource);
}
