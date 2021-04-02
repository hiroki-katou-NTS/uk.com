package nts.uk.cnv.ws.event.order;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import nts.uk.cnv.app.td.alteration.CreateDdlService;
import nts.uk.cnv.app.td.alteration.query.AlterationSummaryQuery;
import nts.uk.cnv.app.td.command.event.order.OrderCommand;
import nts.uk.cnv.app.td.command.event.order.OrderCommandHandler;
import nts.uk.cnv.app.td.finder.event.OrderEventFinder;
import nts.uk.cnv.dom.td.alteration.summary.AlterationSummary;
import nts.uk.cnv.dom.td.devstatus.DevelopmentProgress;
import nts.uk.cnv.dom.td.event.AddedResultDto;
import nts.uk.cnv.dom.td.event.order.OrderEvent;
import nts.uk.cnv.dom.td.tabledefinetype.databasetype.DatabaseType;
import nts.uk.cnv.ws.alteration.summary.AlterationDevStatusDto;

@Path("td/event/order")
@Produces(MediaType.APPLICATION_JSON)
public class OrderWebService {

	@Inject
	private OrderCommandHandler orderCommandHandler;

	@Inject
	private OrderEventFinder orderEventFinder;

	@Inject
	AlterationSummaryQuery alterationSummaryQuery;

	@Inject
	private CreateDdlService createDdlService;

	@POST
	@Path("save")
	public AddedResultDto save(OrderCommand command) {
		return orderCommandHandler.handle(command);
	}

	@GET
	@Path("get/{orderId}")
	public List<AlterationDevStatusDto> get(@PathParam("orderId") String orderId) {
		return  alterationSummaryQuery.getDevState(orderId, DevelopmentProgress.ordered());
	}

	@GET
	@Path("getFeatureAlter/{featureId}")
	public List<AlterationSummary> getFeatureAlter(@PathParam("featureId") String featureId) {
		return alterationSummaryQuery.getOfNotOrderedByFeature(featureId);
	}

	@GET
	@Path("getDdl/{orderId}/{rdbmsType}")
	public Response getDdlByOrder(@PathParam("orderId") String orderId, @PathParam("rdbmsType") String rdbmsType){
		String diffDDL = createDdlService.createByOrderEvent(orderId, DatabaseType.valueOf(rdbmsType));
		if(diffDDL.isEmpty()) {
			diffDDL = "差分が存在しません。IDを確認してください。";
		}
        return Response.ok(diffDDL)
				  .header(
						  "Content-Disposition",
						  "attachment; filename=ddl.sql" )
				  .build();
	}

	@GET
	@Path("getList")
	public List<OrderEvent> getList() {
		return orderEventFinder.getList();
	}
}
