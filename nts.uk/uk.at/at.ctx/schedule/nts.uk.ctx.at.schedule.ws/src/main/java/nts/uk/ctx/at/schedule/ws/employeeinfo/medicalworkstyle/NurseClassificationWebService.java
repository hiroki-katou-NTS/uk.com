package nts.uk.ctx.at.schedule.ws.employeeinfo.medicalworkstyle;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.command.employeeinfo.medicalworkstyle.AddNurseCategoryCommandHandler;
import nts.uk.ctx.at.schedule.app.command.employeeinfo.medicalworkstyle.DeleteNurseCategoryCommand;
import nts.uk.ctx.at.schedule.app.command.employeeinfo.medicalworkstyle.DeleteNurseCategoryCommandHandler;
import nts.uk.ctx.at.schedule.app.command.employeeinfo.medicalworkstyle.NurseCategoryCommand;
import nts.uk.ctx.at.schedule.app.command.employeeinfo.medicalworkstyle.UpdateNurseCategoryCommandHandler;
import nts.uk.ctx.at.schedule.app.find.employeeinfo.medicalworkstyle.NurseCategoryDto;
import nts.uk.ctx.at.schedule.app.find.employeeinfo.medicalworkstyle.NurseCategoryQuery;
import nts.uk.ctx.at.schedule.app.find.employeeinfo.medicalworkstyle.NurseDetailCategoryDto;
import nts.uk.ctx.at.schedule.app.find.employeeinfo.medicalworkstyle.NurseDetailCategoryQuery;

/**
 * @author ThanhNX
 *
 */
@Path("ctx/at/nurse/classification")
@Produces(MediaType.APPLICATION_JSON)
public class NurseClassificationWebService extends WebService {

	@Inject
	private NurseDetailCategoryQuery nurseDetailCategoryQuery;

	@Inject
	private NurseCategoryQuery nurseCategoryQuery;

	@Inject
	private AddNurseCategoryCommandHandler addNurseCategoryCommandHandler;

	@Inject
	private UpdateNurseCategoryCommandHandler updateNurseCategoryCommandHandler;

	@Inject
	private DeleteNurseCategoryCommandHandler deleteNurseCategoryCommandHandler;

	@POST
	@Path("findAll")
	public List<NurseCategoryDto> findAll() {
		return this.nurseCategoryQuery.getListNurseCategory();
	}

	@POST
	@Path("find/{code}")
	public NurseDetailCategoryDto findDetail(@PathParam("code") String nurseClcode) {
		return this.nurseDetailCategoryQuery.getDetailNurseCategory(nurseClcode);
	}

	@POST
	@Path("register")
	public void register(NurseCategoryCommand command) {
		addNurseCategoryCommandHandler.handle(command);
	}

	@POST
	@Path("update")
	public void update(NurseCategoryCommand command) {
		updateNurseCategoryCommandHandler.handle(command);
	}

	@POST
	@Path("delete")
	public void delete(DeleteNurseCategoryCommand command) {
		deleteNurseCategoryCommandHandler.handle(command);
	}

}
