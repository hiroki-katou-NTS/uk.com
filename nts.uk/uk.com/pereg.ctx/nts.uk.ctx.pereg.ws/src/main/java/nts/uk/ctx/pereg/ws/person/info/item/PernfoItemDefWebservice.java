package nts.uk.ctx.pereg.ws.person.info.item;

import java.util.ArrayList;
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
		List<SimpleItemDef> lstRe = new ArrayList<>();
		switch(ctgCd){
			case "CS00037":
				lstRe = getItemsCS00037();
			break;
			case "CS00038":
				lstRe = getItemsCS00038();
			break;
			case "CS00039":
				lstRe = getItemsCS00039();
			break;
		}
		return lstRe;
		//return itemDefFinder.getSingpleItemDef(ctgCd);
	}
	
	private List<SimpleItemDef> getItemsCS00038(){
		List<SimpleItemDef> lst = new ArrayList<>();
		lst.add(new SimpleItemDef("IS00398", "積立年休付与日", true));
		lst.add(new SimpleItemDef("IS00399", "積立年休期限日",  true));
		lst.add(new SimpleItemDef("IS00400", "積立年休期限切れ状態", true));
		lst.add(new SimpleItemDef("IS00401", "積立年休使用状況", true));
		lst.add(new SimpleItemDef("IS00403", "付与数", true));
		lst.add(new SimpleItemDef("IS00404", "使用数", true));
		lst.add(new SimpleItemDef("IS00405", "使用日数", true));
		lst.add(new SimpleItemDef("IS00406", "上限超過消滅日数", true));
		lst.add(new SimpleItemDef("IS00408", "残数", true));
		return lst;
		
	}
	private List<SimpleItemDef> getItemsCS00039(){
		List<SimpleItemDef> lst = new ArrayList<>();
		lst.add(new SimpleItemDef("IS00409", "付与日", true));
		lst.add(new SimpleItemDef("IS00410", "期限日",  true));
		lst.add(new SimpleItemDef("IS00411", "期限切れ状態", true));
		lst.add(new SimpleItemDef("IS00412", "使用状況", true));
		lst.add(new SimpleItemDef("IS00413", "付与数", true));
		lst.add(new SimpleItemDef("IS00414", "付与日数", true));
		lst.add(new SimpleItemDef("IS00415", "付与時間", true));//
		lst.add(new SimpleItemDef("IS00416", "使用数", true));
		lst.add(new SimpleItemDef("IS00417", "使用日数", true));
		lst.add(new SimpleItemDef("IS00418", "使用時間", true));//
		lst.add(new SimpleItemDef("IS00419", "上限超過消滅日数", true));
		lst.add(new SimpleItemDef("IS00420", "上限超過消滅時間", true));//
		lst.add(new SimpleItemDef("IS00421", "残数", true));
		lst.add(new SimpleItemDef("IS00422", "残日数", true));//
		lst.add(new SimpleItemDef("IS00423", "残時間", true));
		return lst;
	}
	private List<SimpleItemDef> getItemsCS00037(){
		List<SimpleItemDef> lst = new ArrayList<>();
		lst.add(new SimpleItemDef("IS00385", "年休付与日", true));
		lst.add(new SimpleItemDef("IS00386", "年休期限日",  true));
		lst.add(new SimpleItemDef("IS00387", "年休期限切れ状態", true));
		lst.add(new SimpleItemDef("IS00388", "年休使用状況", true));
		lst.add(new SimpleItemDef("IS00389", "付与数", true));
		lst.add(new SimpleItemDef("IS00390", "付与日数", true));
		lst.add(new SimpleItemDef("IS00391", "付与時間", false));
		lst.add(new SimpleItemDef("IS00392", "使用数", true));
		lst.add(new SimpleItemDef("IS00393", "使用日数", true));
		lst.add(new SimpleItemDef("IS00394", "使用時間", false));
		lst.add(new SimpleItemDef("IS00395", "残数", true));
		lst.add(new SimpleItemDef("IS00396", "残日数", true));
		lst.add(new SimpleItemDef("IS00397", "残時間", false));
		return lst;
	}
}
