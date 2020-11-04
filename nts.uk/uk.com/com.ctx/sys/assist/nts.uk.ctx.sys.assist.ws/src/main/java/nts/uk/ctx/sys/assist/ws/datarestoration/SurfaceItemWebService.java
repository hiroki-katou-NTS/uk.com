package nts.uk.ctx.sys.assist.ws.datarestoration;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.uk.ctx.sys.assist.app.find.datarestoration.ItemSetDto;
import nts.uk.ctx.sys.assist.app.find.datarestoration.SetScreenItemFinder;
import nts.uk.ctx.sys.assist.app.find.datarestoration.SurfaceItemDto;
import nts.uk.ctx.sys.assist.app.find.datarestoration.SurfaceItemFinder;

@Path("ctx/sys/assist/datarestoration")
@Produces("application/json")
public class SurfaceItemWebService {
	
	@Inject
	private SurfaceItemFinder surfaceItemFinder;
	
	@Inject
	private SetScreenItemFinder setScreenItemFinder;
	
	@POST
	@Path("findTableList/{dataRecoveryProcessId}")
	public List<SurfaceItemDto> surfaceItem(@PathParam("dataRecoveryProcessId") String dataRecoveryProcessId) {
		return surfaceItemFinder.getSurfaceItemById(dataRecoveryProcessId);
	}
	
	@POST
	@Path("setScreenItem/{dataStorageProcessId}")
	public List<ItemSetDto> setScreenItem(@PathParam("dataStorageProcessId") String dataStorageProcessId) {
		return setScreenItemFinder.findScreenItem(dataStorageProcessId);
	}
}
