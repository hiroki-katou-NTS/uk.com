package nts.uk.screen.at.ws.dailyschedule;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceConfigInfo;
import nts.uk.file.at.app.export.dailyschedule.WorkScheduleOutputCondition;
import nts.uk.file.at.app.export.dailyschedule.WorkScheduleOutputGenerator;
import nts.uk.file.at.app.export.dailyschedule.WorkScheduleOutputQuery;
import nts.uk.file.at.app.export.dailyschedule.WorkScheduleOutputQueryDto;

/**
 * The Class Kwr001WebService.
 * @author HoangNDH
 */
@Path("screen/at/dailyschedule")
@Produces("application/json")
public class Kwr001WebService extends WebService {
	
	/** The service. */
	@Inject
	DailyPerformanceExportService service;
	
	/**
	 * Export data.
	 *
	 * @param dto the dto
	 * @return the export service result
	 */
	@POST
	@Path("export")
	public DailyPerformanceExportResponse exportData(WorkScheduleOutputQueryDto dto) {
		WorkScheduleOutputQuery query = WorkScheduleOutputQuery.createFromJavaType(dto);
		ExportServiceResult result = service.start(query);
		DailyPerformanceExportResponse response = new DailyPerformanceExportResponse();
		response.setTaskId(result.getTaskId());
		return response;
	}
}
