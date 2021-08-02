package nts.uk.screen.com.ws.cmf.cmf001;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.screen.com.app.cmf.cmf001.Cmf001cScreenQuery;
import nts.uk.screen.com.app.cmf.cmf001.ItemMappingListItemDto;

@Path("exio/input/setting")
@Produces(MediaType.APPLICATION_JSON)
public class Cmf001cWebService {

	@Inject
	private Cmf001cScreenQuery screenC;
	
	@Path("list")
	@POST
	public List<ItemMappingListItemDto> getList(Cmf001cScreenQuery.ParamGetList param) {
		return screenC.getList(param);
	}
}
