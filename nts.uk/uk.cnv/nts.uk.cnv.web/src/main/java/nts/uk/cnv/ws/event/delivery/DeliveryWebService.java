package nts.uk.cnv.ws.event.delivery;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import nts.uk.cnv.app.td.alteration.CreateDdlService;
import nts.uk.cnv.app.td.alteration.query.AlterationSummaryQuery;
import nts.uk.cnv.app.td.command.event.delivery.DeliveryCommand;
import nts.uk.cnv.app.td.command.event.delivery.DeliveryCommandHandler;
import nts.uk.cnv.app.td.finder.event.DeliveryEventFinder;
import nts.uk.cnv.dom.td.alteration.summary.AlterationSummary;
import nts.uk.cnv.dom.td.event.AddedResultDto;
import nts.uk.cnv.dom.td.event.delivery.DeliveryEvent;
import nts.uk.cnv.dom.td.tabledefinetype.databasetype.DatabaseType;

@Path("td/event/delivery")
@Produces(MediaType.APPLICATION_JSON)
public class DeliveryWebService {

	@Inject
	private DeliveryCommandHandler deliveryCommandHandler;

	@Inject
	private DeliveryEventFinder deliveryEventFinder;

	@Inject
	AlterationSummaryQuery alterationSummaryQuery;

	@Inject
	private CreateDdlService createDdlService;

	@POST
	@Path("save")
	public AddedResultDto save(DeliveryCommand command) {
		return deliveryCommandHandler.handle(command);
	}

	@GET
	@Path("get/{deliveryId}")
	public List<AlterationSummary> get(@PathParam("deliveryId") String deliveryId){
		return deliveryEventFinder.getBy(deliveryId);
	}

	@GET
	@Path("getDdl/{deliveryId}/{rdbmsType}")
	public String getDdlByDelivery(@PathParam("deliveryId") String deliveryId, @PathParam("rdbmsType") String rdbmsType) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(createDdlService.createByDeliveryEvent(deliveryId, DatabaseType.valueOf(rdbmsType)));
	}

	@GET
	@Path("getList")
	public List<DeliveryEvent> getList() {
		return deliveryEventFinder.getList();
	}
}
