package nts.uk.cnv.ws.event.order;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.time.GeneralDateTime;
import nts.uk.cnv.app.td.command.event.order.OrderCommand;
import nts.uk.cnv.app.td.command.event.order.OrderCommandHandler;
import nts.uk.cnv.app.td.finder.event.OrderEventFinder;
import nts.uk.cnv.dom.td.alteration.AlterationMetaData;
import nts.uk.cnv.dom.td.alteration.summary.AlterationSummary;
import nts.uk.cnv.dom.td.alteration.summary.DevelopmentState;
import nts.uk.cnv.dom.td.schema.TableIdentity;

@Path("td/event/order")
@Produces(MediaType.APPLICATION_JSON)
public class OrderWebService {

	@Inject
	private OrderCommandHandler orderCommandHandler;
	
	@Inject
	private OrderEventFinder orderEventFinder;

	@POST
	@Path("add")
	public void add(OrderCommand command) {
		orderCommandHandler.handle(command);
	}
	
	@GET
	@Path("get/{orderId}")
	public List<AlterationSummary> get(@PathParam("orderId") String orderId) {
//		return Arrays.asList(new AlterationSummary("id",
//				GeneralDateTime.now(), "KRCDT", DevelopmentState.ORDERED, new AlterationMetaData("俺","こめんと"), "ふぇーちゃー"));
		
		return this.orderEventFinder.getBy(orderId);
	}
	
	@GET
	@Path("getByFeature/{featureId}")
	public List<AlterationSummary> getByFeature(@PathParam("orderId") String featureId) {
		return Arrays.asList(new AlterationSummary("id",
				GeneralDateTime.now(), "KRCDT", DevelopmentState.ORDERED, new AlterationMetaData("俺","こめんと"), "ふぇーちゃー"));
		
		//return this.orderEventFinder.getBy(orderId).orElse(null);
	}
}
