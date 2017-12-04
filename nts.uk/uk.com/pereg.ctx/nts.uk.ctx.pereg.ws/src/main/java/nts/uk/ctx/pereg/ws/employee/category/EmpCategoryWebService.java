package nts.uk.ctx.pereg.ws.employee.category;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pereg.app.find.employee.category.EmployeeCategoryFinder;
import nts.uk.ctx.pereg.app.find.person.category.PerInfoCtgFullDto;

@Path("bs/employee/category")
@Produces(MediaType.APPLICATION_JSON)
public class EmpCategoryWebService extends WebService {

	@Inject
	private EmployeeCategoryFinder empCtgFinder;;

	@POST
	@Path("getAll/{employeeId}")
	public List<PerInfoCtgFullDto> getAllPerInfoCtg(@PathParam("employeeId") String employeeIdSelected) {
		return empCtgFinder.getAllPerInfoCtg(employeeIdSelected);
	}

	//
	@POST
	@Path("getListInfoCategory/{employeeId}/{categoryId}")
	public List<Object> getListInfoCategory(@PathParam("employeeId") String employeeId,
			@PathParam("categoryId") String categoryId) {
		empCtgFinder.getListInfoCtgByCtgIdAndSid(employeeId, categoryId);
		return null;
	}
}
