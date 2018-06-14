package nts.uk.ctx.sys.assist.ws.datarestoration;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.sys.assist.app.find.datarestoration.SurfaceItemDto;
import nts.uk.ctx.sys.assist.app.find.datarestoration.SurfaceItemFinder;

@Path("ctx/sys/assist/app")
@Produces("application/json")
public class SurfaceItemWebService {
	@Inject
	private SurfaceItemFinder surfaceItemFinder;
	@POST
	@Path("findAll")
	public List<SurfaceItemDto> getAll() {
		return surfaceItemFinder.getAllTableList();
	}
}
