package nts.uk.ctx.pr.core.ws.insurance.labor.businesstype;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.core.app.insurance.labor.businesstype.command.InsuranceBusinessTypeUpdateCommand;
import nts.uk.ctx.core.app.insurance.labor.businesstype.command.InsuranceBusinessTypeUpdateCommandHandler;

@Path("pr/insurance/labor/businesstype")
@Produces("application/json")
public class InsuranceBusinessTypeService {
	@Inject
	InsuranceBusinessTypeUpdateCommandHandler update;

	@POST
	@Path("update")
	public void update(InsuranceBusinessTypeUpdateCommand command) {
		this.update.handle(command);
	}
}
