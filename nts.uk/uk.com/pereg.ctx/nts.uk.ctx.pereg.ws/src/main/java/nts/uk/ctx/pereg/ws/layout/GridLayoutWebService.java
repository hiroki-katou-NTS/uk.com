package nts.uk.ctx.pereg.ws.layout;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.GridEmployeeDto;
import nts.uk.ctx.pereg.app.find.processor.GridPeregProcessor;
import nts.uk.shr.pereg.app.find.PeregGridQuery;

@Path("ctx/pereg/grid-layout")
@Produces(MediaType.APPLICATION_JSON)
public class GridLayoutWebService extends WebService {
	
	@Inject
	GridPeregProcessor gridProcessor;

	@POST
	@Path("get-data")
	public GridEmployeeDto get(PeregGridQuery query) {
		return gridProcessor.getGridLaoyout(query);
	}
}
