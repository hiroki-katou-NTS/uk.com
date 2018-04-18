package nts.uk.ctx.at.function.ws.holidaysremaining;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.function.app.find.holidaysremaining.HdRemainManageDto;
import nts.uk.ctx.at.function.app.find.holidaysremaining.HdRemainManageFinder;

@Path("at/function/holidaysremaining")
@Produces("application/json")
public class HolidaysremainingWebService extends WebService {

	/* Finder */
	@Inject
	private HdRemainManageFinder hdRemainManageFinder;
	

	@POST
	@Path("findAll")
	public List<HdRemainManageDto> getHdRemainManageList() {
		return this.hdRemainManageFinder.findAll();
	}

}
