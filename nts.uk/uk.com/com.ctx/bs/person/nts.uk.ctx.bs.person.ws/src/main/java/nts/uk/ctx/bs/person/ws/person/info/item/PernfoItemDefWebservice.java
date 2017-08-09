package nts.uk.ctx.bs.person.ws.person.info.item;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import find.person.info.item.PerInfoItemDefDto;
import find.person.info.item.PerInfoItemDefFinder;

@Path("ctx/bs/person/info/ctgItem")
@Produces("application/json")
public class PernfoItemDefWebservice {
	
	@Inject
	private PerInfoItemDefFinder itemDefFinder;

	@POST
	@Path("findby/categoryId/{perInfoCtgId}")
	public List<PerInfoItemDefDto> getAllPerInfoItemDefByCtgId(@PathParam("perInfoCtgId") String perInfoCtgId) {
		return itemDefFinder.getAllPerInfoItemDefByCtgId(perInfoCtgId);
	}
	
	@POST
	@Path("findby/itemId/{Id}")
	public PerInfoItemDefDto getPerInfoItemDefById(@PathParam("Id") String Id) {
		return itemDefFinder.getPerInfoItemDefById(Id);
	}
	
	@POST
	@Path("findby/listItemId")
	public List<PerInfoItemDefDto> getPerInfoItemDefByListId(List<String> listItemDefId) {
		return itemDefFinder.getPerInfoItemDefByListId(listItemDefId);
	}
	
	//service for screen Layout
	@POST
	@Path("findby/categoryId/{perInfoCtgId}")
	public List<PerInfoItemDefDto> getAllPerInfoItemDefByCtgIdForLayout(@PathParam("perInfoCtgId") String perInfoCtgId) {
		return itemDefFinder.getAllPerInfoItemDefByCtgIdForLayout(perInfoCtgId);
	}
	
	@POST
	@Path("findby/itemId/{Id}")
	public PerInfoItemDefDto getPerInfoItemDefByIdForLayout(@PathParam("Id") String Id) {
		return itemDefFinder.getPerInfoItemDefByIdForLayout(Id);
	}
	
	@POST
	@Path("findby/listItemId")
	public List<PerInfoItemDefDto> getPerInfoItemDefByListIdForLayout(List<String> listItemDefId) {
		return itemDefFinder.getPerInfoItemDefByListIdForLayout(listItemDefId);
	}
}
