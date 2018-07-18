package nts.uk.ctx.exio.ws.exo.outputitem;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.exio.app.find.exo.item.StdOutItemDto;
import nts.uk.ctx.exio.app.find.exo.item.StdOutputItemFinder;

@Path("exio/exo/outputitem")
@Produces("application/json")
public class StdOutputItemWebService extends WebService {
	@Inject
	private StdOutputItemFinder stdOutputItemFinder;
	
	@POST
	@Path("getOutItems")
	public List<StdOutItemDto> getOutItem(String condSetCd) {
		return stdOutputItemFinder.getOutItems(condSetCd);
	}
}
