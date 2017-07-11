/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.ws.company.organization.employee;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.basic.app.find.company.organization.employee.EmployeeDto;
import nts.uk.ctx.basic.app.find.company.organization.employee.EmployeeFinder;
import nts.uk.ctx.basic.app.query.employee.EmployeeSearchQueryProcessor;
import nts.uk.ctx.basic.app.query.employee.EmployeeSearchData;
import nts.uk.ctx.basic.app.query.employee.EmployeeSearchListQuery;
import nts.uk.ctx.basic.app.query.employee.EmployeeSearchQuery;

@Path("basic/organization/employee")
@Produces(MediaType.APPLICATION_JSON)
public class EmployeeWebService extends WebService {

	@Inject
	private EmployeeFinder employeeFinder;
	
	/** The employee query processor. */
	@Inject
	private EmployeeSearchQueryProcessor employeeQueryProcessor;
	
	@POST
	@Path("getPersonIdByEmployeeCode/{employeeCode}")
	public EmployeeDto getPersonIdByEmployeeCode(@PathParam("employeeCode") String employeeCode) {
		return this.employeeFinder.getPersonIdByEmployeeCode(employeeCode).orElse(null);
	}
	
	@POST
	@Path("getListPersonIdByEmployeeCode")
	public List<EmployeeDto> getListPersonIdByEmployeeCode(List<String> lstEmployeeCode) {
		return this.employeeFinder.getListPersonIdByEmployeeCode(lstEmployeeCode);
	}
	
	/**
	 * Gets the all employee.
	 *
	 * @return the all employee
	 */
	@POST
	@Path("getAllEmployee")
	public List<EmployeeDto> getAllEmployee() {
		return this.employeeFinder.getAllEmployee();
	}
	
	
	/**
	 * Search all employee.
	 *
	 * @param baseDate the base date
	 * @return the list
	 */
	@POST
	@Path("allemployee")
	public List<EmployeeSearchData> searchAllEmployee(GeneralDate baseDate){
		return this.employeeQueryProcessor.searchAllEmployee(baseDate);
	}
	
	/**
	 * Search employee by login.
	 *
	 * @param baseDate the base date
	 * @return the list
	 */
	@POST
	@Path("onlyemployee")
	public List<EmployeeSearchData> searchEmployeeByLogin(GeneralDate baseDate){
		return this.employeeQueryProcessor.searchEmployeeByLogin(baseDate);
	}
	/**
	 * Search mode employee.
	 *
	 * @param input the input
	 * @return the list
	 */
	@POST
	@Path("advanced")
	public List<EmployeeSearchData> searchModeEmployee(EmployeeSearchQuery input) {
		return this.employeeQueryProcessor.searchModeEmployee(input);
	}
	
	
	/**
	 * Search of work place.
	 *
	 * @param baseDate the base date
	 * @return the list
	 */
	@POST
	@Path("ofworkplace")
	public List<EmployeeSearchData> searchOfWorkplace(GeneralDate baseDate){
		return this.employeeQueryProcessor.searchOfWorkplace(baseDate);
	}
	
	/**
	 * Search of work place child.
	 *
	 * @param baseDate the base date
	 * @return the list
	 */
	@POST
	@Path("workplacechild")
	public List<EmployeeSearchData> searchWorkplaceChild(GeneralDate baseDate){
		return this.employeeQueryProcessor.searchWorkplaceChild(baseDate);
	}
	
	/**
	 * Search work place of employee.
	 *
	 * @param baseDate the base date
	 * @return the list
	 */
	@POST
	@Path("workplaceemp")
	public List<String> searchWorkplaceOfEmployee(GeneralDate baseDate){
		return this.employeeQueryProcessor.searchWorkplaceOfEmployee(baseDate);
	}
	
	
	/**
	 * Gets the of selected employee.
	 *
	 * @param input the input
	 * @return the of selected employee
	 */
	@POST
	@Path("getoffselect")
	public List<EmployeeSearchData> getOfSelectedEmployee(EmployeeSearchListQuery input){
		return this.employeeQueryProcessor.getOfSelectedEmployee(input);
	}
}
