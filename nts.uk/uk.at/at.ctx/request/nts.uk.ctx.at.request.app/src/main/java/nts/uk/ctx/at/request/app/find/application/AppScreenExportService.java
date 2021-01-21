package nts.uk.ctx.at.request.app.find.application;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.at.request.app.command.application.applicationlist.AppListInfoCmd;
import nts.uk.ctx.at.request.dom.application.AppScreenGenerator;

/**
 * @author anhnm
 *
 */
@Stateless
public class AppScreenExportService extends ExportService<AppScreenQuery> {

	@Inject
	private AppScreenGenerator generator;

	@Override
	protected void handle(ExportServiceContext<AppScreenQuery> context) {
		AppScreenQuery query = context.getQuery();
		int appListAtr = query.getAppListAtr();
		AppListInfoCmd lstApp = query.getLstApp();
		String programName = query.getProgramName();
		FileGeneratorContext exportContext = context.getGeneratorContext();

		generator.generate(exportContext, appListAtr, lstApp.toDomain(), programName);
	}

}
