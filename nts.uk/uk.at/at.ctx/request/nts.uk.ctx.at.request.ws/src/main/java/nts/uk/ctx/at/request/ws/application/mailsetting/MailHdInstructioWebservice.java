package nts.uk.ctx.at.request.ws.application.mailsetting;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.request.app.find.setting.company.mailsetting.MailHdInstructionDto;
import nts.uk.ctx.at.request.app.find.setting.company.mailsetting.MailHdInstructionFinder;

@Path("at/request/application/mail")
@Produces("application/json")
public class MailHdInstructioWebservice {
	@Inject
	private MailHdInstructionFinder mailFinder;
	@POST
	@Path("holiday")
	public MailHdInstructionDto getAppSet(){
		 return mailFinder.findByComId();
	}
}
