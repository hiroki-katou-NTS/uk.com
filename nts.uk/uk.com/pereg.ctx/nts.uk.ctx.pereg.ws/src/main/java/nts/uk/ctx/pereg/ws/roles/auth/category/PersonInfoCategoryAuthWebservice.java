package nts.uk.ctx.pereg.ws.roles.auth.category;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pereg.app.command.roles.auth.category.UpdatePersonInfoCategoryAuthCommand;
import nts.uk.ctx.pereg.app.command.roles.auth.category.UpdatePersonInfoCategoryAuthCommandHandler;
import nts.uk.ctx.pereg.app.find.roles.auth.category.PersonInfoCategoryAuthDto;
import nts.uk.ctx.pereg.app.find.roles.auth.category.PersonInfoCategoryAuthFinder;
import nts.uk.ctx.pereg.app.find.roles.auth.category.PersonInfoCategoryDetailDto;

@Path("ctx/pereg/roles/auth/category")
@Produces("application/json")
public class PersonInfoCategoryAuthWebservice extends WebService {
	@Inject
	PersonInfoCategoryAuthFinder personInfoCategoryAuthFinder;
	
	@Inject
	UpdatePersonInfoCategoryAuthCommandHandler update;

	@POST
	@Path("findAllCategory/{roleId}")
	public List<PersonInfoCategoryDetailDto> getAllCategory(@PathParam("roleId") String roleId) {
		return personInfoCategoryAuthFinder.getAllCategory(roleId);

	}

	@POST
	@Path("find/{roleId}/{personCategoryAuthId}")
	public PersonInfoCategoryAuthDto getCategoryAuth(@PathParam("roleId") String roleId,
			@PathParam("personCategoryAuthId") String personCategoryAuthId) {
		return personInfoCategoryAuthFinder.getDetailPersonCategoryAuthByPId(roleId, personCategoryAuthId);
	}

	@POST
	@Path("find/categoryAuth/{roleId}")
	public List<PersonInfoCategoryAuthDto> getAllCategoryAuthByRoleId(@PathParam("roleId") String roleId) {
		return personInfoCategoryAuthFinder.getAllCategoryAuth(roleId);

	}
	@POST
	@Path("update")
	public void updateCategoryAuth(UpdatePersonInfoCategoryAuthCommand command){
		this.update.handle(command);
	}
}
