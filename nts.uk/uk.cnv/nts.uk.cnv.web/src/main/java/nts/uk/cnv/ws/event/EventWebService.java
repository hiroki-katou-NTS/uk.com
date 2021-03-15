package nts.uk.cnv.ws.event;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.cnv.app.td.command.AcceptCommand;
import nts.uk.cnv.app.td.command.AcceptCommandHandler;
import nts.uk.cnv.app.td.command.DeliveryCommand;
import nts.uk.cnv.app.td.command.DeliveryCommandHandler;
import nts.uk.cnv.app.td.command.OrderCommand;
import nts.uk.cnv.app.td.command.OrderCommandHandler;

@Path("td/event")
@Produces(MediaType.APPLICATION_JSON)
public class EventWebService {

	@Inject
	OrderCommandHandler orderCommandHandler;

	@Inject
	DeliveryCommandHandler deliveryCommandHandler;

	@Inject
	AcceptCommandHandler acceptCommandHandler;

	@POST
	@Path("order")
	public void order(OrderCommand command) {
		orderCommandHandler.handle(command);
	}

	@POST
	@Path("delivery")
	public void order(DeliveryCommand command) {
		deliveryCommandHandler.handle(command);
	}

	@POST
	@Path("accept")
	public void order(AcceptCommand command) {
		acceptCommandHandler.handle(command);
	}
}
