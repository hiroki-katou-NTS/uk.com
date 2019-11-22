package nts.uk.file.com.app.person.matrix;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.file.com.app.person.matrix.datasource.PersonInfoMatrixDataSource;
@Stateless
public class PersonInfoMatrixExportService extends ExportService<PersonInfoMatrixDataSource>{

	@Inject
	private PersonInfoMatrixGenerator matrixGenerator;
	
	@Override
	protected void handle(ExportServiceContext<PersonInfoMatrixDataSource> context) {
		val dataSource = context.getQuery();
		matrixGenerator.generate(context.getGeneratorContext(), dataSource);
	}

}
