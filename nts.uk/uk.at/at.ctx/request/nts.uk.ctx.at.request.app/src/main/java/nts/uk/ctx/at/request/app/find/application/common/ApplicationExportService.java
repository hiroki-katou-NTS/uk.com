package nts.uk.ctx.at.request.app.find.application.common;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.common.service.print.ApplicationGenerator;
import nts.uk.ctx.at.request.dom.application.common.service.print.CommonAppPrint;
import nts.uk.ctx.at.request.dom.application.common.service.print.PrintContentOfApp;
import nts.uk.ctx.at.request.dom.application.common.service.print.PrintContentOfEachApp;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.shr.com.context.AppContexts;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@Stateless
public class ApplicationExportService extends ExportService<AppPrintQuery> {
	
	@Inject
	private CommonAppPrint commonAppPrint;

	@Inject
	private ApplicationGenerator applicationGenerator;
	
	@Override
	protected void handle(ExportServiceContext<AppPrintQuery> context) {
		AppPrintQuery appPrintQuery = context.getQuery();
		String companyID = AppContexts.user().companyId();
		AppDispInfoStartupOutput appDispInfoStartupOutput = appPrintQuery.appDispInfoStartupOutput.toDomain();
		Application application = appDispInfoStartupOutput.getAppDetailScreenInfo().get().getApplication();
		Optional<PrintContentOfEachApp> opPrintContentOfEachApp = Optional.empty();
		if(appPrintQuery.opPrintContentOfEachApp != null) {
			opPrintContentOfEachApp = Optional.of(appPrintQuery.opPrintContentOfEachApp.toDomain());
		}
				
		PrintContentOfApp printContentOfApp = commonAppPrint.print(
				companyID, 
				application.getAppID(), 
				appDispInfoStartupOutput, 
				opPrintContentOfEachApp);
		
		applicationGenerator.generate(
				context.getGeneratorContext(), 
				printContentOfApp,
				application.getAppType());
	}

}
