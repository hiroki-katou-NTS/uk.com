package nts.uk.ctx.pereg.ws.licence;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.pereg.app.find.licence.LicenseCheckFinder;
import nts.uk.ctx.pereg.app.find.licence.LicenseUpperLimit;
import nts.uk.ctx.pereg.app.find.licence.LicensenCheckDto;

@Path("ctx/pereg/license")
@Produces("application/json")
public class LicenceCheckWebService {

	@Inject
	private LicenseCheckFinder licenseCheckFinder;
	
	@POST
	@Path("checkDipslayStart")
	public LicensenCheckDto checkDipslayStart() {
		return licenseCheckFinder.checkLicenseStartCPS001();
	}
	
	@POST
	@Path("checkLicense")
	public LicenseUpperLimit checkLicense() {
		return licenseCheckFinder.checkLicenseUpverLimit(GeneralDate.today());
	}
}
