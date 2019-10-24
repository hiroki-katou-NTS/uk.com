package nts.uk.ctx.pereg.ws.employee.category;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.ctx.pereg.app.find.employee.category.EmpCtgFinder;
import nts.uk.ctx.pereg.app.find.person.category.PerInfoCtgFullDto;
import nts.uk.shr.pereg.app.ComboBoxObject;
import nts.uk.shr.pereg.app.find.PeregQuery;

/**
 * 
 *
 */

@Path("ctx/pereg/employee/category")
@Produces(MediaType.APPLICATION_JSON)
public class PeregEmpCtgWebService {
	@Inject
	private EmpCtgFinder empCtgFinder;
	
//	@Inject
//	private PeregBusinessTypeFinder PeregBusinessTypeFinder;

	@POST
	@Path("getall/{employeeId}")
	public List<PerInfoCtgFullDto> getAllPerInfoCtg(@PathParam("employeeId") String employeeIdSelected) {
		return empCtgFinder.getAllPerInfoCtg(employeeIdSelected);
	}
	
	@POST
	@Path("get-all-cps003/{employeeId}")
	public List<PerInfoCtgFullDto> getAllPerInfoCtgCps003(@PathParam("employeeId") String employeeIdSelected) {
		return empCtgFinder.getAllPerInfoCtgByCps003(employeeIdSelected);
	}

	//
	@POST
	@Path("getlistinfocategory")
	public List<ComboBoxObject> getListInfoCategory(PeregQuery query) {
		return empCtgFinder.getListInfoCtgByCtgIdAndSid(query);
	}
}
