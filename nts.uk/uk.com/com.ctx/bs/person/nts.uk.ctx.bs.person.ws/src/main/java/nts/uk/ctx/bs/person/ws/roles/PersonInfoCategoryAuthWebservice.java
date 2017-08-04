package nts.uk.ctx.bs.person.ws.roles;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import find.roles.auth.category.PersonInfoCategoryAuthDto;
import find.roles.auth.category.PersonInfoCategoryAuthFinder;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.bs.person.dom.person.role.auth.category.PersonInfoCategoryDetail;

@Path("ctx/bs/person/roles/auth/category")
@Produces("application/json")
public class PersonInfoCategoryAuthWebservice extends WebService {
	@Inject
	PersonInfoCategoryAuthFinder personInfoCategoryAuthFinder;

	
	@POST
	@Path("findAllCategory/{roleId}")
	public List<PersonInfoCategoryDetail> getAllCategory(@PathParam("roleId") String roleId) {
		return personInfoCategoryAuthFinder.getAllCategory(roleId);

	}

	
	@POST
	@Path("find/{personCategoryAuthId}")
	public PersonInfoCategoryAuthDto getAuthDetailByPId(
			@PathParam("personCategoryAuthId") String personCategoryAuthId) {
		return personInfoCategoryAuthFinder.getDetailPersonCategoryAuthByPId(personCategoryAuthId);
	}

}
