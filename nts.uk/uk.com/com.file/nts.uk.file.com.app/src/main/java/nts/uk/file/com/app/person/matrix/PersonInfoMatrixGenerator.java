package nts.uk.file.com.app.person.matrix;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.file.com.app.person.matrix.datasource.PersonInfoMatrixDataSource;

public interface PersonInfoMatrixGenerator {
	void generate(FileGeneratorContext generatorContext, PersonInfoMatrixDataSource dataSource);
}
