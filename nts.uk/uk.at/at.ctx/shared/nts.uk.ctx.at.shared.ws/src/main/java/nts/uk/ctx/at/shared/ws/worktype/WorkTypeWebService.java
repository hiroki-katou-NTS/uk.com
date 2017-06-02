package nts.uk.ctx.at.shared.ws.worktype;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.find.worktype.WorkTypeDto;
import nts.uk.ctx.at.shared.app.find.worktype.WorkTypeFinder;
@Path("at/share/worktype")
@Produces("application/json")
public class WorkTypeWebService extends WebService{

	@Inject
	private WorkTypeFinder find;
	
	@POST
	@Path("getpossibleworktype")
	public List<WorkTypeDto> getPossibleWorkType(List<String> lstPossible) {
		return this.find.getPossibleWorkType(lstPossible);
	}
}
