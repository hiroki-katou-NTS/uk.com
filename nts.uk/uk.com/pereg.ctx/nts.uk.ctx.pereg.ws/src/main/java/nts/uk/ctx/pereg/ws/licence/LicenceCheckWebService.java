package nts.uk.ctx.pereg.ws.licence;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("ctx/pereg/license")
@Produces("application/json")
public class LicenceCheckWebService {


	@POST
	@Path("checkDipslay")
	public Boolean checkDipslay() {
		return true;
	}
}
