package nts.uk.file.com.app.person.matrix;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.file.com.app.person.matrix.datasource.PersonMatrixErrorDataSource;

public interface PersonInfoMatrixErrorGenerator {
	void generate(FileGeneratorContext generatorContext, PersonMatrixErrorDataSource dataSource);
}
