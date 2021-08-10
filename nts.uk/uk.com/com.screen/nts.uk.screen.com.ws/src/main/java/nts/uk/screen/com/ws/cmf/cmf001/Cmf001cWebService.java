package nts.uk.screen.com.ws.cmf.cmf001;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.screen.com.app.cmf.cmf001.c.get.GetImportableItemAndConstraint;
import nts.uk.screen.com.app.cmf.cmf001.c.get.ImportableItemDto;

@Path("screen/com/cmf/cmf001")
@Produces(MediaType.APPLICATION_JSON)
public class Cmf001cWebService {

	@Inject
	private GetImportableItemAndConstraint importableItem;
	
	@POST
	@Path("importable-item/{settingCode}/{itemNo}")
	public ImportableItemDto getDomainConstraint(
			@PathParam("settingCode") String settingCode,
			@PathParam("itemNo") int itemNo) {
		
		return importableItem.get(settingCode, itemNo);
	}
}
