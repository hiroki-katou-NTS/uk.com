package nts.uk.ctx.bs.person.ws.person.info.item;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import find.person.info.item.PersonInfoItemDefDto;
import find.person.info.item.PersonInfoItemDefFinder;
import nts.arc.layer.ws.WebService;
/**
 * The class PersonInfoItemDefWebservice 
 * @author lanlt 
 *
 */
@Path("ctx/bs/person/info/item")
@Produces("application/json")
public class PersonInfoItemDefWebservice extends WebService{

	@Inject
	private PersonInfoItemDefFinder itemDefFinder;
	
	@POST
	@Path("findby/categoryId/{perInfoCtgId}")
	public List<PersonInfoItemDefDto> getAllPerInfoItemDefByCtgId(@PathParam("perInfoCtgId") String perInfoCtgId) {
		return itemDefFinder.getAllPerInfoItemDefByCategoryIdWithoutSetItem(perInfoCtgId);
	}
}
