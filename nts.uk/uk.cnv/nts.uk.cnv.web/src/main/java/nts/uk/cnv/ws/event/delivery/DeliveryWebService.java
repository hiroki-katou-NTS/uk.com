package nts.uk.cnv.ws.event.delivery;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.cnv.app.cnv.finder.DeliveryEventFinder;
import nts.uk.cnv.dom.td.alteration.summary.AlterationSummary;

@Path("td/event/delivery")
@Produces(MediaType.APPLICATION_JSON)
public class DeliveryWebService {

	@Inject
	DeliveryEventFinder deliveryEventFinder;

	@GET
	@Path("get/{deliveryId}")
	public List<AlterationSummary> getDelivery(@PathParam("deliveryId") String deliveryId){
		return deliveryEventFinder.getBy(deliveryId);
	}
}
