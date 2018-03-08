package nts.uk.ctx.exio.ws.exi.item;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.uk.ctx.exio.app.command.exi.item.StdAcceptItemCommand;
import nts.uk.ctx.exio.app.find.exi.item.StdAcceptItemDto;
import nts.uk.ctx.exio.app.find.exi.item.StdAcceptItemFinder;

@Path("exio/exi/item")
@Produces("application/json")
public class StdAcceptItemWebService {
	@Inject
	private StdAcceptItemFinder stdAcceptItem;

	@POST
	@Path("getAllStdAcceptItem/{systemType}/{conditionSetCd}")
	public List<StdAcceptItemDto> getAllStdAcceptItem(@PathParam("systemType") int systemType,
			@PathParam("conditionSetCd") String conditionSetCd) {
		return this.stdAcceptItem.getStdAcceptItems(systemType, conditionSetCd);
	}
	
	@POST
	@Path("register")
	public void getAllStdAcceptItem(List<StdAcceptItemCommand> listItem) {
		int length = listItem.size();
		System.out.println("data size: " + length);
//		return this.stdAcceptItem.getStdAcceptItems(systemType, conditionSetCd);
	}
}
