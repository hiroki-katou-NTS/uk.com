package nts.uk.ctx.at.shared.ws.worktime;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.find.worktime.WorkTimeFinder;
import nts.uk.ctx.at.shared.app.find.worktime.dto.WorkTimeDto;

/**
 * 
 * @author Doan Duy Hung
 *
 */

@Path("at/shared/worktime")
@Produces("application/json")
public class WorkTimeWebService extends WebService{
	
	@Inject
	private WorkTimeFinder workTimeFinder;
	
	@POST
	@Path("findByCodeList")
	public List<WorkTimeDto> findByCodeList(List<String> codelist){
		return this.workTimeFinder.findByCodeList(codelist);
	}
	
	@POST
	@Path("findByTime")
	public List<WorkTimeDto> findByTime(WorkTimeCommandFinder command){
		return this.workTimeFinder.findByTime(command.codelist, command.startAtr, command.startTime, command.endAtr, command.endTime);
	}
}
@Data
@NoArgsConstructor
class WorkTimeCommandFinder {
	List<String> codelist;
	int startAtr;
	int startTime;
	int endAtr;
	int endTime;
}
