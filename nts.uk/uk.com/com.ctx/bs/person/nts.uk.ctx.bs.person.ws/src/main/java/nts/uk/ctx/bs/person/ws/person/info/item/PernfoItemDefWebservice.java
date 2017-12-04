package nts.uk.ctx.bs.person.ws.person.info.item;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import command.person.info.item.AddItemCommand;
import command.person.info.item.AddItemCommandHandler;
import command.person.info.item.RemoveItemCommand;
import command.person.info.item.RemoveItemCommandHandler;
import command.person.info.item.UpdateItemChangeCommand;
import command.person.info.item.UpdateItemChangeCommandHandler;
import command.person.info.item.UpdateItemCommand;
import command.person.info.item.UpdateItemCommandHandler;
import command.person.info.item.UpdateOrderItemChangeCommand;
import command.person.info.item.UpdateOrderItemChangeCommandHandler;
import command.person.info.item.UpdatePerInfoItemDefCopy;
import command.person.info.item.UpdatePerInfoItemDefCopyCommandHandler;
import find.person.info.item.PerInfoItemChangeDefDto;
import find.person.info.item.PerInfoItemDefDto;
import find.person.info.item.PerInfoItemDefFinder;
import find.person.info.item.PerInfoItemDefFullEnumDto;
import find.person.info.item.PerInfoItemDefMapDto;
import nts.arc.layer.app.command.JavaTypeResult;
import nts.arc.layer.ws.WebService;

@Path("ctx/bs/person/info/ctgItem")
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
	
	@Inject 
	private UpdatePerInfoItemDefCopyCommandHandler updatePerInfoItemDefCopyCommandHandler;

	@POST
	@Path("findby/categoryId1/{perInfoCtgId}/{personEmployeeType}")
	public PerInfoItemDefFullEnumDto getAllPerInfoItemDefByCtgId(@PathParam("perInfoCtgId") String perInfoCtgId, @PathParam("personEmployeeType") int personEmployeeType) {
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
	public PerInfoItemChangeDefDto getPerInfoItemDefByIdOfOtherCompany(@PathParam("Id") String Id,@PathParam("personEmployeeType") int personEmployeeType) {
		return itemDefFinder.getPerInfoItemDefByIdOfOtherCompany(Id, personEmployeeType);
	}

	@POST
	@Path("findby/itemId/{Id}")
	public PerInfoItemChangeDefDto getPerInfoItemDefById(@PathParam("Id") String Id) {
		return itemDefFinder.getPerInfoItemDefById(Id);
	}

	@POST
	@Path("findby/listItemId")
	public List<PerInfoItemDefDto> getPerInfoItemDefByListId(List<String> listItemDefId) {
		return itemDefFinder.getPerInfoItemDefByListId(listItemDefId);
	}
	//vinhpx: start
	@POST
	@Path("findby/getPerInfoItemByCtgId")
	public List<PerInfoItemDefMapDto> getPerInfoItemByCtgId(String ctgId){
		return itemDefFinder.getPerInfoDefById(ctgId);
	}
	
	@POST
	@Path("update/updatePerInfoItemDefCopy")
	public void updatePerInfoItemDefCopy(UpdatePerInfoItemDefCopy command){
		this.updatePerInfoItemDefCopyCommandHandler.handle(command);
	}
	//vinhpx; end

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
}
