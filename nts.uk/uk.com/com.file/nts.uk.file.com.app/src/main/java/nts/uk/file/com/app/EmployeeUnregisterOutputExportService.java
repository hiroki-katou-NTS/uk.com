package nts.uk.file.com.app;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class EmployeeUnregisterOutputExportService extends ExportService<GeneralDate> {

	@Inject
	private EmployeeUnregisterApprovalRootRepository employRepo;
	
	@Inject
	private EmployeeUnregisterOutputGenerator employgenerator;
	
	@Override
	protected void handle(ExportServiceContext<GeneralDate> context) {
		
		String companyId = AppContexts.user().companyId();
		
		// get query parameters
		GeneralDate value = context.getQuery();
		
		// create data source
		val items = this.employRepo.getEmployeeUnregisterOutputLst(companyId, value);
		val dataSource = new EmployeeUnregisterOutputDataSoure(items.getEmployeeUnregisterOutputLst());

		
		// generate file
		this.employgenerator.generate(context.getGeneratorContext(), dataSource);
	}

}
