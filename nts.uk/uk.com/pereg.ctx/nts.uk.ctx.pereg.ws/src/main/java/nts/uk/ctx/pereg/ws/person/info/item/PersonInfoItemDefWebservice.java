package nts.uk.ctx.pereg.ws.person.info.item;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pereg.app.find.person.info.item.PersonInfoItemDefDto;
import nts.uk.ctx.pereg.app.find.person.info.item.PersonInfoItemDefFinder;
/**
 * The class PersonInfoItemDefWebservice 
 * @author lanlt 
 *
 */
@Path("ctx/pereg/person/info/item")
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
