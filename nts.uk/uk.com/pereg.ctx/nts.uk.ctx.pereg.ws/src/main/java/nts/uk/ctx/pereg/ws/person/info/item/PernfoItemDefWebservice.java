package nts.uk.ctx.pereg.ws.person.info.item;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.app.command.JavaTypeResult;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pereg.app.command.person.info.item.AddItemCommand;
import nts.uk.ctx.pereg.app.command.person.info.item.AddItemCommandHandler;
import nts.uk.ctx.pereg.app.command.person.info.item.RemoveItemCommand;
import nts.uk.ctx.pereg.app.command.person.info.item.RemoveItemCommandHandler;
import nts.uk.ctx.pereg.app.command.person.info.item.UpdateItemChangeCommand;
import nts.uk.ctx.pereg.app.command.person.info.item.UpdateItemChangeCommandHandler;
import nts.uk.ctx.pereg.app.command.person.info.item.UpdateItemCommand;
import nts.uk.ctx.pereg.app.command.person.info.item.UpdateItemCommandHandler;
import nts.uk.ctx.pereg.app.command.person.info.item.UpdateOrderItemChangeCommand;
import nts.uk.ctx.pereg.app.command.person.info.item.UpdateOrderItemChangeCommandHandler;
import nts.uk.ctx.pereg.app.find.person.info.item.ItemRequiredBackGroud;
import nts.uk.ctx.pereg.app.find.person.info.item.PerInfoItemChangeDefDto;
import nts.uk.ctx.pereg.app.find.person.info.item.PerInfoItemDefDto;
import nts.uk.ctx.pereg.app.find.person.info.item.PerInfoItemDefFinder;
import nts.uk.ctx.pereg.app.find.person.info.item.PerInfoItemDefFullEnumDto;
import nts.uk.ctx.pereg.app.find.person.info.item.SimpleItemDef;

@Path("ctx/pereg/person/info/ctgItem")
@Produces("application/json")
public class PernfoItemDefWebservice extends WebService {

	@Inject
	private PerInfoItemDefFinder itemDefFinder;

	@Inject
	private AddItemCommandHandler addItemCm;

	@Inject
	private UpdateItemCommandHandler updateItemCm;

	@Inject
	private RemoveItemCommandHandler removeItemCm;

	@Inject
	private UpdateItemChangeCommandHandler updateItemChange;

	@Inject
	private UpdateOrderItemChangeCommandHandler updateOrderItemChange;

	@POST
	@Path("findby/categoryId1/{perInfoCtgId}/{personEmployeeType}")
	public PerInfoItemDefFullEnumDto getAllPerInfoItemDefByCtgId(@PathParam("perInfoCtgId") String perInfoCtgId,
			@PathParam("personEmployeeType") int personEmployeeType) {
		return itemDefFinder.getAllPerInfoItemDefByCtgId(perInfoCtgId, personEmployeeType);
	}

	@POST
	@Path("findby/categoryId/{perInfoCtgId}/{isAbolition}")
	public List<PerInfoItemDefDto> getAllPerInfoItemDefByCtgId(@PathParam("perInfoCtgId") String perInfoCtgId,
			@PathParam("isAbolition") String isAbolition) {
		return itemDefFinder.getAllPerInfoItemDefByCtgId(perInfoCtgId, isAbolition);
	}

	@POST
	@Path("findby/itemIdOfOtherCompany/{Id}/{personEmployeeType}")
	public PerInfoItemChangeDefDto getPerInfoItemDefByIdOfOtherCompany(@PathParam("Id") String Id,
			@PathParam("personEmployeeType") int personEmployeeType) {
		return itemDefFinder.getPerInfoItemDefByIdOfOtherCompany(Id, personEmployeeType);
	}

	@POST
	@Path("findby/itemId/{Id}/{personEmployeeType}")
	public PerInfoItemChangeDefDto getPerInfoItemDefById(@PathParam("Id") String Id,
			@PathParam("personEmployeeType") int personEmployeeType) {
		return itemDefFinder.getPerInfoItemDefById(Id, personEmployeeType);
	}

	@POST
	@Path("findby/listItemId")
	public List<PerInfoItemDefDto> getPerInfoItemDefByListId(List<String> listItemDefId) {
		return itemDefFinder.getPerInfoItemDefByListId(listItemDefId);
	}

	// service for screen Layout
	@POST
	@Path("layout/findby/categoryId/{perInfoCtgId}")
	public List<PerInfoItemDefDto> getAllPerInfoItemDefByCtgIdForLayout(
			@PathParam("perInfoCtgId") String perInfoCtgId) {
		return itemDefFinder.getAllPerInfoItemDefByCtgIdForLayout(perInfoCtgId);
	}

	@POST
	@Path("layout/findby/itemId/{Id}")
	public PerInfoItemDefDto getPerInfoItemDefByIdForLayout(@PathParam("Id") String Id) {
		return itemDefFinder.getPerInfoItemDefByIdForLayout(Id);
	}

	@POST
	@Path("layout/findby/listItemId")
	public List<PerInfoItemDefDto> getPerInfoItemDefByListIdForLayout(List<String> listItemDefId) {
		return itemDefFinder.getPerInfoItemDefByListIdForLayout(listItemDefId);
	}

	// to anh Vuong
	// test hieu nang Layout
	@POST
	@Path("layout/findby/listItemIdv2")
	public List<PerInfoItemDefDto> getPerInfoItemDefByListIdForLayoutV2(List<String> listItemDefId) {
		return itemDefFinder.getPerInfoItemDefByIds(listItemDefId);
	}

	@POST
	@Path("layout/finditem/required")
	public List<String> getRequiredIds() {
		return itemDefFinder.getRequiredIds();
	}

	@POST
	@Path("add")
	public JavaTypeResult<String> addItemDef(AddItemCommand addItemCommand) {
		return new JavaTypeResult<String>(addItemCm.handle(addItemCommand));
	}

	@POST
	@Path("update")
	public JavaTypeResult<String> updateItemDef(UpdateItemCommand updateItemCommand) {
		return new JavaTypeResult<String>(updateItemCm.handle(updateItemCommand));
	}

	@POST
	@Path("remove")
	public JavaTypeResult<String> removeItemDef(RemoveItemCommand removeCommand) {
		return new JavaTypeResult<String>(removeItemCm.handle(removeCommand));

	}

	// service update item change
	@POST
	@Path("updateItemChange")
	public void updateItemChange(UpdateItemChangeCommand command) {
		this.updateItemChange.handle(command);
	}

	@POST
	@Path("SetOrder")
	public void updateItemChange(UpdateOrderItemChangeCommand command) {
		this.updateOrderItemChange.handle(command);
	}

	@POST
	@Path("checkExistItem/{selectionItemId}")
	public boolean checkExistedSelectionItemId(@PathParam("selectionItemId") String selectionItemId) {
		return this.itemDefFinder.checkExistedSelectionItemId(selectionItemId);
	}

	// lalt start

	@POST
	@Path("layout/findAll/required")
	public List<ItemRequiredBackGroud> getAllRequiredIdsByCid() {
		return itemDefFinder.getAllRequiredIdsByCompanyID();
	}

	@POST
	@Path("layout/findAll/required/{ctgId}")
	public List<ItemRequiredBackGroud> getAllRequiredIdsByCtgId(@PathParam("ctgId") String ctgId) {
		return itemDefFinder.getAllItemRequiredIdsByCtgId(ctgId);
	}

	@POST
	@Path("check/itemData/{itemId}")
	public boolean checkItemData(@PathParam("itemId") String itemId) {
		return this.itemDefFinder.isCheckData(itemId);
	}

	// lanlt end
	
	@POST
	@Path("findby/ctg-cd/{ctgcd}")
	public List<SimpleItemDef> getSimpleItemDefsByCtgCd(@PathParam("ctgcd") String ctgCd) {
		return itemDefFinder.getSingpleItemDef(ctgCd);
	}
	
	
}
