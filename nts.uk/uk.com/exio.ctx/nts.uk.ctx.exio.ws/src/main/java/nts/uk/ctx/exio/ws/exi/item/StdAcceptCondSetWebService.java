package nts.uk.ctx.exio.ws.exi.item;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.exio.app.find.exi.item.StdAcceptItemDto;
import nts.uk.ctx.exio.app.find.exi.item.StdAcceptItemFinder;

@Path("exio/exi/item")
@Produces("application/json")
public class StdAcceptCondSetWebService {
	@Inject
	private StdAcceptItemFinder stdAcceptItem;

	@POST
	@Path("getAllStdAcceptItem")
	public List<StdAcceptItemDto> getAllStdAcceptItem() {
		return this.stdAcceptItem.getStdAcceptItem(0, "001");
	}
}
