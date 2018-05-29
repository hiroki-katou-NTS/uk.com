package nts.uk.screen.at.ws.dailyschedule;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.file.at.app.export.dailyschedule.WorkScheduleOutputGenerator;
import nts.uk.file.at.app.export.dailyschedule.WorkScheduleOutputQuery;

@Stateless
public class DailyPerformanceExportService extends ExportService<WorkScheduleOutputQuery>  {
	@Inject
	WorkScheduleOutputGenerator generator;
	
	@Override
	protected void handle(ExportServiceContext<WorkScheduleOutputQuery> context) {
		WorkScheduleOutputQuery query = context.getQuery();
		
		this.generator.generate(context.getGeneratorContext(), query);
	}
}
