package nts.uk.cnv.ws.alteration.summary;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.cnv.app.td.alteration.query.AlterationSummaryQuery;
import nts.uk.cnv.dom.td.alteration.summary.AlterationSummary;

@Path("td/altersummary")
@Produces(MediaType.APPLICATION_JSON)
public class AlterationSummaryWebService {
	
	@Inject
	AlterationSummaryQuery alterSummary;
	
	@GET
	@Path("getAll/{featureId}")
	public List<AlterationSummary> getAll(@PathParam("featureId") String featureId) {
		return alterSummary.getAll(featureId);
	}
	
	@GET
	@Path("getByFeature/not-ordered/{featureId}")
	public List<AlterationSummary> getOfNotOrderedByFeature(@PathParam("featureId") String featureId) {
		return alterSummary.getOfNotOrderedByFeature(featureId);
	}
	
	@GET
	@Path("getByEvent/ordered/{eventId}")
	public List<AlterationSummary> getOfOrderedByEvent(@PathParam("eventId") String eventId) {
		return alterSummary.getOfOrderedByEvent(eventId);
	}

}
