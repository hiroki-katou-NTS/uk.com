package nts.uk.ctx.pr.report.ws.payment.comparing;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.report.app.payment.comparing.masterpage.find.PersonInfoDto;
import nts.uk.ctx.pr.report.app.payment.comparing.masterpage.find.PersonInfoFinder;

@Path("report/payment/comparing/masterpage")
@Produces("application/json")
public class MasterPageWebservice extends WebService {

	@Inject
	private PersonInfoFinder personInfoFinder;
	
	@POST
	@Path("getpersons")
	public List<PersonInfoDto> getPersonInfo() {
		return this.personInfoFinder.getPersonInfo();
	}
}
