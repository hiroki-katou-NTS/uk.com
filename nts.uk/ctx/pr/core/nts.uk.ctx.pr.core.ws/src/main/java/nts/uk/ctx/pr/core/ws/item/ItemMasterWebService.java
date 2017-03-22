package nts.uk.ctx.pr.core.ws.item;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.command.itemmaster.AddItemMasterCommand;
import nts.uk.ctx.pr.core.app.command.itemmaster.AddItemMasterCommandHandler;
import nts.uk.ctx.pr.core.app.command.itemmaster.DeleteItemMasterCommand;
import nts.uk.ctx.pr.core.app.command.itemmaster.DeleteItemMasterCommandHandler;
import nts.uk.ctx.pr.core.app.command.itemmaster.UpdateItemMasterCommand;
import nts.uk.ctx.pr.core.app.command.itemmaster.UpdateItemMasterCommandHandler;
import nts.uk.ctx.pr.core.app.find.itemmaster.ItemMasterFinder;
import nts.uk.ctx.pr.core.app.find.itemmaster.dto.ItemMasterDto;
import nts.uk.ctx.pr.core.app.find.itemmaster.dto.ItemMasterSEL_3_Dto;
import nts.uk.ctx.pr.core.dom.itemmaster.ItemAtr;

@Path("pr/core/item")
@Produces("application/json")
public class ItemMasterWebService extends WebService {
	@Inject
	private ItemMasterFinder itemFinder;
	@Inject
	private UpdateItemMasterCommandHandler updateHandler;
	@Inject
	private AddItemMasterCommandHandler addHandler;
	@Inject
	private DeleteItemMasterCommandHandler deleteHandler;

	@POST
	@Path("findall/avepay/time")
	public List<ItemMasterDto> findByAvePayAtrTime() {
		return itemFinder.findAllByItemAtr(ItemAtr.TIMES);
	}

	@POST
	@Path("findall/category/{categoryAtr}")
	public List<ItemMasterDto> findBy(@PathParam("categoryAtr") int categoryAtr) {
		return itemFinder.findBy(categoryAtr);
	}

	@POST
	@Path("find/{categoryAtr}/{itemCode}")
	public ItemMasterDto find(@PathParam("categoryAtr") int categoryAtr, @PathParam("itemCode") String itemCode) {
		return itemFinder.find(categoryAtr, itemCode);
	}

	@POST
	@Path("add")
	public void add(AddItemMasterCommand command) {
		this.addHandler.handle(command);
	}

	@POST
	@Path("delete")
	public void delete(DeleteItemMasterCommand command) {
		this.deleteHandler.handle(command);
	}

	@POST
	@Path("update")
	public void update(UpdateItemMasterCommand command) {
		this.updateHandler.handle(command);
	}

	@POST
	@Path("findAllItemMaster/{ctgAtr}/{dispSet}")
	public List<ItemMasterDto> findAllNoAvePayAtr(
			@PathParam("ctgAtr") int ctgAtr,
			@PathParam("dispSet") int dispSet) {
		return itemFinder.findAllNoAvePayAtr(ctgAtr, dispSet);
	}

	@POST
	@Path("find_SEL_3/{categoryAtr}")
	public List<ItemMasterSEL_3_Dto> find_SEL_3(@PathParam("categoryAtr") int categoryAtr) {
		return itemFinder.find_SEL_3(categoryAtr);
	}

}
