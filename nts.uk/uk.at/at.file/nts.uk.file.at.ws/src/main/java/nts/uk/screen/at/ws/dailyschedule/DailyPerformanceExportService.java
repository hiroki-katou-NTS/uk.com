package nts.uk.screen.at.ws.dailyschedule;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.task.data.TaskDataSetter;
import nts.uk.file.at.app.export.dailyschedule.WorkScheduleOutputGenerator;
import nts.uk.file.at.app.export.dailyschedule.WorkScheduleOutputQuery;

/**
 * The Class DailyPerformanceExportService.
 * @author HoangNDH
 */
@Stateless
public class DailyPerformanceExportService extends ExportService<WorkScheduleOutputQuery>  {
	
	/** The generator. */
	@Inject
	WorkScheduleOutputGenerator generator;
	
	/* (non-Javadoc)
	 * @see nts.arc.layer.app.file.export.ExportService#handle(nts.arc.layer.app.file.export.ExportServiceContext)
	 */
	@Override
	protected void handle(ExportServiceContext<WorkScheduleOutputQuery> context) {
		WorkScheduleOutputQuery query = context.getQuery();
		
		TaskDataSetter setter = context.getDataSetter();
		
		this.generator.generate(context.getGeneratorContext(), setter, query);
	}
}
