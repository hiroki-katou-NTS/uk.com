package nts.uk.ctx.bs.person.ws.roles;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import find.roles.auth.item.PersonInfoItemAuthDto;
import find.roles.auth.item.PersonInfoItemAuthFinder;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.bs.person.dom.person.role.auth.item.PersonInfoItemDetail;

@Path("ctx/bs/person/roles/auth/item")
@Produces("application/json")
public class PersonInfoItemAuthWebservice extends WebService {
	@Inject
	PersonInfoItemAuthFinder personInfoItemAuthFinder;

	@POST
	@Path("findAll")
	public List<PersonInfoItemAuthDto> getAllPersonItemAuth() {
		return personInfoItemAuthFinder.getAllPersonItemAuth();

	}

	@POST
	@Path("find/{roleId}/{personCategoryAuthId}")
	public List<PersonInfoItemAuthDto> getAllPersonItemAuthByCategory(@PathParam("roleId") String roleId,
			@PathParam("personCategoryAuthId") String personCategoryAuthId) {
		return personInfoItemAuthFinder.getAllPersonItemAuthByCategory(roleId, personCategoryAuthId);

	}
	
	@POST
	@Path("findAllItem/{personInfoCategoryAuthId}")
	public List<PersonInfoItemDetail> getAllItemDetail(@PathParam("personInfoCategoryAuthId") String personInfoCategoryAuthId) {
		return personInfoItemAuthFinder.getAllItemDetail(personInfoCategoryAuthId);

	}

	@POST
	@Path("find/{roleId}/{personCategoryAuthId}/{personItemDefId}")
	public PersonInfoItemAuthDto getDetailPersonItemAuth(@PathParam("roleId") String roleId,
			@PathParam("personCategoryAuthId") String personCategoryAuthId,
			@PathParam("personItemDefId") String personItemDefId) {
		return personInfoItemAuthFinder.getDetailPersonItemAuth(roleId, personCategoryAuthId, personItemDefId).get();

	}
}
