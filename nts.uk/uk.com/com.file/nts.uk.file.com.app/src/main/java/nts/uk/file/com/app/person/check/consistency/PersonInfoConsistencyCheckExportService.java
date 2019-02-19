package nts.uk.file.com.app.person.check.consistency;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.file.com.app.person.check.consistency.datasource.EmployeInfoErrorDataSource;
@Stateless
public class PersonInfoConsistencyCheckExportService extends ExportService<List<EmployeInfoErrorDataSource>>{

	@Inject
	private PersonInfoConsistencyCheckGenerator checkGenerator;
	
	@Override
	protected void handle(ExportServiceContext<List<EmployeInfoErrorDataSource>> context) {
		this.checkGenerator.generate(context.getGeneratorContext(), context.getQuery());
		
	}

}
