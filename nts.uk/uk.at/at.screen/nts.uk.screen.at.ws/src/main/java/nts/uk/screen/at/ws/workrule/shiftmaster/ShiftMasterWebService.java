package nts.uk.screen.at.ws.workrule.shiftmaster;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.screen.at.app.ksm015.find.WorkStyleDto;
import nts.uk.screen.at.app.ksm015.find.WorkStyleScreenQuery;

@Path("ctx/at/screen/workrule/shiftmaster")
@Produces("application/json")
public class ShiftMasterWebService {
	@Inject
	WorkStyleScreenQuery query;

	@POST
	@Path("getWorkStyle")
	public Integer getWorkStyle(WorkStyleDto dto){
		return this.query.getWorkStyle(dto);
	}
}
