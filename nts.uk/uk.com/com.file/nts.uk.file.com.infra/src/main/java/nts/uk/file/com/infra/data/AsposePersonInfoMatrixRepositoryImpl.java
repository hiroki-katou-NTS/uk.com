package nts.uk.file.com.infra.data;

import javax.ejb.Stateless;

import nts.uk.file.com.app.person.matrix.PersonInfoMatrixRepository;
import nts.uk.file.com.app.person.matrix.datasource.PersonInfoMatrixDataSource;
@Stateless
public class AsposePersonInfoMatrixRepositoryImpl implements PersonInfoMatrixRepository{
	@Override
	public PersonInfoMatrixDataSource getFullInfoPersonMatrix() {
		// TODO Auto-generated method stub
		return null;
	}

}

