package nts.uk.screen.com.ws.cmf.cmf001;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.com.app.cmf.cmf001.d.get.ImportableItemDto;
import nts.uk.screen.com.app.cmf.cmf001.d.get.GetImportableItem;

@Path("screen/com/cmf/cmf001/b")
@Produces("application/json")
public class Cmf001dWebService extends WebService {
	
	@Inject
	private GetImportableItem importableItem;
	
	@POST
	@Path("get/importableitem/{importingGroupId}")
	public List<ImportableItemDto> get(@PathParam("importingGroupId") int importingGroupId) {
		List<ImportableItemDto> result = importableItem.get(importingGroupId);
		return result;
	}
}
