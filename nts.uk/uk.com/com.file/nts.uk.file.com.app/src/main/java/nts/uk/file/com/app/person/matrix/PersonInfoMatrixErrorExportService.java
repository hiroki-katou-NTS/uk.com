package nts.uk.file.com.app.person.matrix;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.file.com.app.person.matrix.datasource.PersonMatrixErrorDataSource;
@Stateless
public class PersonInfoMatrixErrorExportService  extends ExportService<PersonMatrixErrorDataSource> {
	@Inject
	private PersonInfoMatrixErrorGenerator matrixErrorGenerator;
	@Override
	protected void handle(ExportServiceContext<PersonMatrixErrorDataSource> context) {
		val dataSource = context.getQuery();
		matrixErrorGenerator.generate(context.getGeneratorContext(), dataSource);
	}
}
