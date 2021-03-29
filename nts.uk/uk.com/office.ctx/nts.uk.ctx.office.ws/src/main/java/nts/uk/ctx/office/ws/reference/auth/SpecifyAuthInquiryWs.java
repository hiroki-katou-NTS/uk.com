package nts.uk.ctx.office.ws.reference.auth;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.office.app.command.reference.auth.SpecifyAuthInquiryCommand;
import nts.uk.ctx.office.app.command.reference.auth.SpecifyAuthInquiryCommandHandler;

@Path("ctx/office/reference/auth")
@Produces("application/json")
public class SpecifyAuthInquiryWs extends WebService {
	
	@Inject
	private SpecifyAuthInquiryCommandHandler authCommand;
	
	@POST
	@Path("save")
	public void saveSpecifyAuthInquiry(SpecifyAuthInquiryCommand command) {
		authCommand.handle(command);
	}
}
