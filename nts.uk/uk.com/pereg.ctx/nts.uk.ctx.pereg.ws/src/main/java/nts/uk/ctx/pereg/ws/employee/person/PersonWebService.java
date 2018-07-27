package nts.uk.ctx.pereg.ws.employee.person;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeInfo;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeSimpleInfo;
import nts.uk.ctx.pereg.app.find.deleteemployee.EmployeeToDeleteDto;
import nts.uk.ctx.pereg.app.find.employee.GetHeaderOfCPS001Finder;
import nts.uk.ctx.pereg.app.find.person.info.PersonDto;
import nts.uk.ctx.pereg.app.find.person.info.PersonFinder;

@Path("bs/employee/person")
@Produces(MediaType.APPLICATION_JSON)
public class PersonWebService extends WebService {
	@Inject
	private PersonFinder personFinder;

	@Inject
	private GetHeaderOfCPS001Finder empFinder;

	@POST
	@Path("findByEmployeeId/{employeeId}")
	public PersonDto findByEmployeeId(@PathParam("employeeId") String employeeId) {
		return personFinder.getPersonByEmpId(employeeId);
	}

	@POST
	@Path("findBypId/{pid}")
	public EmployeeToDeleteDto findBypId(@PathParam("pid") String pid) {
		return personFinder.getPersonBypId(pid);
	}

	/**
	 * get header for cps001
	 */
	@POST
	@Path("get-header/{employeeId}")
	public EmployeeInfo getEmployeeInfo(@PathParam(value = "employeeId") String employeeId) {
		return this.empFinder.getEmployeeInfo(employeeId);
	}
	
	@POST
	@Path("get-list-emps")
	public List<EmployeeSimpleInfo> getEmployeeInfo(List<String> lstId) {
		return this.empFinder.getList(lstId);
	}
}