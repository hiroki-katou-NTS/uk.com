package nts.uk.screen.at.ws.ksu.ksu002.a;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.at.app.query.ksu.ksu002.a.TheInitialDisplayDate;
import nts.uk.screen.at.app.query.ksu.ksu002.a.TheInitialDisplayDateDto;

@Path("screen/ksu/ksu002/")
@Produces("application/json")
public class Ksu002AWebService extends WebService {

	@Inject
	private TheInitialDisplayDate theInitialDisplayDate;

	@POST
	@Path("getInitialDate")
	public TheInitialDisplayDateDto get() {
		return this.theInitialDisplayDate.getInitialDisplayDate();
	}
}
