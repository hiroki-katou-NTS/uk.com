package nts.uk.ctx.pereg.ws.roles.auth.item;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pereg.app.find.roles.auth.item.ItemAuth;
import nts.uk.ctx.pereg.app.find.roles.auth.item.PersonInfoItemAuthFinder;

@Path("ctx/pereg/roles/auth/item")
@Produces("application/json")
public class PersonInfoItemAuthWebservice extends WebService {
	@Inject
	PersonInfoItemAuthFinder personInfoItemAuthFinder;

	@POST
	@Path("findAllItem/{roleId}/{personInfoCategoryAuthId}")
	public ItemAuth getAllItemDetail(@PathParam("roleId") String roleId,
			@PathParam("personInfoCategoryAuthId") String personInfoCategoryAuthId) {
		return personInfoItemAuthFinder.getAllItemDetail(roleId, personInfoCategoryAuthId);

	}

}
