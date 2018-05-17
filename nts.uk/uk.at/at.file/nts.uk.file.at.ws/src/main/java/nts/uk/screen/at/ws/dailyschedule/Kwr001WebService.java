package nts.uk.screen.at.ws.dailyschedule;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceConfigInfo;
import nts.uk.file.at.app.export.dailyschedule.WorkScheduleOutputCondition;
import nts.uk.file.at.app.export.dailyschedule.WorkScheduleOutputGenerator;
import nts.uk.file.at.app.export.dailyschedule.WorkScheduleOutputQuery;
import nts.uk.file.at.app.export.dailyschedule.WorkScheduleOutputQueryDto;

@Path("screen/at/dailyschedule")
@Produces("application/json")
public class Kwr001WebService extends WebService {
	@Inject
	WorkScheduleOutputGenerator generator;
	
	@POST
	@Path("export")
	public void exportData(WorkScheduleOutputQueryDto dto) {
		WorkScheduleOutputQuery query = WorkScheduleOutputQuery.createFromJavaType(dto);
		generator.generate(query);
	}
}
