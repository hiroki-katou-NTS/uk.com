package nts.uk.ctx.basic.ws.organization.payclassification;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.basic.app.command.organization.payclassification.AddPayClassificationCommand;
import nts.uk.ctx.basic.app.command.organization.payclassification.AddPayClassificationCommandHandler;
import nts.uk.ctx.basic.app.command.organization.payclassification.RemovePayClassificationCommand;
import nts.uk.ctx.basic.app.command.organization.payclassification.RemovePayClassificationCommandHandler;
import nts.uk.ctx.basic.app.command.organization.payclassification.UpdatePayClassificationCommand;
import nts.uk.ctx.basic.app.command.organization.payclassification.UpdatePayClassificationCommandHandler;
import nts.uk.ctx.basic.app.find.organization.payclassification.PayClassificationDto;
import nts.uk.ctx.basic.app.find.organization.payclassification.PayClassificationFinder;



@Path("")
@Produces(MediaType.APPLICATION_JSON)
public class PayClassificationWebService extends WebService {

	@Inject
	private PayClassificationFinder payClassificationFinder;

	@Inject
	private AddPayClassificationCommandHandler addPayClassificationCommandHandler;

	@Inject
	private UpdatePayClassificationCommandHandler updatePayClassificationCommandHandler;

	@Inject
	private RemovePayClassificationCommandHandler removePayClassificationCommandHandler;

	@Path("")
	@POST
	public List<PayClassificationDto> init() {
		return payClassificationFinder.init();
	}

	@Path("")
	@POST
	public void add(AddPayClassificationCommand command) {
		this.addPayClassificationCommandHandler.handle(command);
	}

	@Path("")
	@POST
	public void update(UpdatePayClassificationCommand command) {
		this.updatePayClassificationCommandHandler.handle(command);
	}

	@Path("")
	@POST
	public void remove(RemovePayClassificationCommand command) {
		this.removePayClassificationCommandHandler.handle(command);
	}


}
