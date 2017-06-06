package nts.uk.ctx.at.shared.ws.worktime;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

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
}
