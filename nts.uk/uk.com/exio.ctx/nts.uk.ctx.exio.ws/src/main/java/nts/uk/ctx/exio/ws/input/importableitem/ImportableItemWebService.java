package nts.uk.ctx.exio.ws.input.importableitem;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.exio.app.input.find.importableitem.ImportableItemDto;
import nts.uk.ctx.exio.app.input.find.importableitem.ImportableItemFinder;

@Path("exio/input/importableitem")
@Produces("application/json")
public class ImportableItemWebService extends WebService {
	
	@Inject
	private ImportableItemFinder finder;
	
	@POST
	@Path("find/{importingGroupId}")
	public List<ImportableItemDto> find(@PathParam("importingGroupId") int importingGroupId) {
		List<ImportableItemDto> result = finder.find(importingGroupId);
		return result;
	}

}
