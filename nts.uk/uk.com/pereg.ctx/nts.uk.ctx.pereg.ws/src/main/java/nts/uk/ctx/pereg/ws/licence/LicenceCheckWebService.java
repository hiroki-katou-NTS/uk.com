package nts.uk.ctx.pereg.ws.licence;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.pereg.app.find.layoutdef.CheckWhetherDisplayLicense;

@Path("ctx/pereg/license")
@Produces("application/json")
public class LicenceCheckWebService {

	@Inject
	private CheckWhetherDisplayLicense checkWhetherDisplayLicense;

	@POST
	@Path("checkDipslay")
	public boolean checkDipslay() {
		return checkWhetherDisplayLicense.checkDislay();
	}
}
