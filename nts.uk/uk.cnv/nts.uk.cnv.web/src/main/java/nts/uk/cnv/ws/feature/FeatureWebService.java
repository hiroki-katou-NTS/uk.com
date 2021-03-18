package nts.uk.cnv.ws.feature;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.cnv.app.td.command.feature.FeatureCommand;
import nts.uk.cnv.app.td.command.feature.FeatureCommandHandler;
import nts.uk.cnv.app.td.query.feature.FeatureQuery;
import nts.uk.cnv.ws.alteration.summary.AlterationDevStatusDto;

@Path("td/feature")
@Produces(MediaType.APPLICATION_JSON)
public class FeatureWebService {
	
	@Inject
	FeatureQuery featureQuery;
	
	@Inject
	FeatureCommandHandler featureCommandHandler;
	
	@POST
	@Path("create")
	public void create(FeatureCommand command) {
		featureCommandHandler.handle(command);
	}
	
	@GET
	@Path("getAll")
	public List<FeatureInfoDto> get() {
		return featureQuery.get();
	}
	
	@GET
	@Path("get/detail/{featureId}")
	public List<AlterationDevStatusDto> getDetail(@PathParam("featureId") String featureId) {
		return featureQuery.getDevState(featureId);
	}
}
