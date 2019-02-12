package nts.uk.file.com.app.person.matrix;

import nts.uk.file.com.app.person.matrix.datasource.PersonInfoMatrixDataSource;

public interface PersonInfoMatrixRepository {
	PersonInfoMatrixDataSource getFullInfoPersonMatrix();
}
