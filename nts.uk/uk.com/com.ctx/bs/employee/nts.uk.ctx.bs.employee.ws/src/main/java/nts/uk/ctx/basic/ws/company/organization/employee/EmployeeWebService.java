/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.ws.company.organization.employee;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.app.command.JavaTypeResult;
import nts.arc.layer.ws.WebService;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.app.command.employee.EmployeeDeleteCommand;
import nts.uk.ctx.bs.employee.app.command.employee.EmployeeDeleteCommandHandler;
import nts.uk.ctx.bs.employee.app.command.employee.EmployeeDeleteToRestoreCommand;
import nts.uk.ctx.bs.employee.app.command.employee.RestoreDataEmpCommandHandler;
import nts.uk.ctx.bs.employee.app.find.employee.EmpInfoDto;
import nts.uk.ctx.bs.employee.app.find.employee.EmployeeDto;
import nts.uk.ctx.bs.employee.app.find.employee.EmployeeFinder;
import nts.uk.ctx.bs.employee.app.find.employee.EmployeeToDeleteDetailDto;
import nts.uk.ctx.bs.employee.app.find.employee.EmployeeToDeleteDto;
import nts.uk.ctx.bs.employee.app.find.employee.validateEmpInfoResultDto;
import nts.uk.ctx.bs.employee.app.query.employee.EmployeeSearchData;
import nts.uk.ctx.bs.employee.app.query.employee.EmployeeSearchListData;
import nts.uk.ctx.bs.employee.app.query.employee.EmployeeSearchListQuery;
import nts.uk.ctx.bs.employee.app.query.employee.EmployeeSearchQuery;
import nts.uk.ctx.bs.employee.app.query.employee.EmployeeSearchQueryProcessor;

@Path("basic/organization/employee")
@Produces({ "application/json", "text/plain" })
public class EmployeeWebService extends WebService {

	@Inject
	private EmployeeFinder employeeFinder;
	
	@Inject
	private RestoreDataEmpCommandHandler restoreEmpHandler;
	
	@Inject
	private EmployeeDeleteCommandHandler empDeleteHandler;

	/** The employee query processor. */
	@Inject
	private EmployeeSearchQueryProcessor employeeQueryProcessor;

	@POST
	@Path("getPersonIdByEmployeeCode/{employeeCode}/{baseDate}")
	public EmployeeDto getPersonIdByEmployeeCode(@PathParam("employeeCode") String employeeCode,
			@PathParam("baseDate") Date baseDate) {
		GeneralDate baseDateParam = GeneralDate.legacyDate(baseDate);
		return this.employeeFinder.getPersonIdByEmployeeCode(employeeCode, baseDateParam).orElse(null);
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

	@POST
	@Path("getGenerateEmplCode")
	public JavaTypeResult<String> getGenerateEmplCode(String startLetters) {
		return new JavaTypeResult<String>(this.employeeFinder.getGenerateEmplCode(startLetters));
	}

	@POST
	@Path("getGenerateCardNo")
	public JavaTypeResult<String> getGenerateCardNo(String startLetters) {
		return new JavaTypeResult<String>(this.employeeFinder.getGenerateCardNo(startLetters));
	}
	// sonnlb

	@POST
	@Path("getGenerateEmplCodeAndComId")
	public JavaTypeResult<String> getGenerateEmplCodeAndComId(String startLetters) {
		return new JavaTypeResult<String>(this.employeeFinder.getGenerateEmplCodeAndComId(startLetters));
	}

	@POST
	@Path("validateEmpInfo")
	public validateEmpInfoResultDto validateEmpInfo(EmpInfoDto empInfo) {

		return this.employeeFinder.validateEmpInfo(empInfo);
	}

	// sonnlb
	/**
	 * Search all employee.
	 *
	 * @param baseDate
	 *            the base date
	 * @return the list
	 */
	@POST
	@Path("allemployee")
	public List<EmployeeSearchData> searchAllEmployee(GeneralDate baseDate) {
		return this.employeeQueryProcessor.searchAllEmployee(baseDate);
	}

	/**
	 * Search employee by login.
	 *
	 * @param baseDate
	 *            the base date
	 * @return the list
	 */
	@POST
	@Path("onlyemployee")
	public List<EmployeeSearchData> searchEmployeeByLogin(GeneralDate baseDate) {
		return this.employeeQueryProcessor.searchEmployeeByLogin(baseDate);
	}

	/**
	 * Search mode employee.
	 *
	 * @param input
	 *            the input
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
	 * @param baseDate
	 *            the base date
	 * @return the list
	 */
	@POST
	@Path("ofworkplace")
	public List<EmployeeSearchData> searchOfWorkplace(GeneralDate baseDate) {
		return this.employeeQueryProcessor.searchOfWorkplace(baseDate);
	}

	/**
	 * Search of work place child.
	 *
	 * @param baseDate
	 *            the base date
	 * @return the list
	 */
	@POST
	@Path("workplacechild")
	public List<EmployeeSearchData> searchWorkplaceChild(GeneralDate baseDate) {
		return this.employeeQueryProcessor.searchWorkplaceChild(baseDate);
	}

	/**
	 * Search work place of employee.
	 *
	 * @param baseDate
	 *            the base date
	 * @return the list
	 */
	@POST
	@Path("workplaceemp")
	public List<String> searchWorkplaceOfEmployee(GeneralDate baseDate) {
		return this.employeeQueryProcessor.searchWorkplaceOfEmployee(baseDate);
	}

	/**
	 * Gets the of selected employee.
	 *
	 * @param input
	 *            the input
	 * @return the of selected employee
	 */
	@POST
	@Path("getoffselect")
	public List<EmployeeSearchData> getOfSelectedEmployee(EmployeeSearchListQuery input) {
		return this.employeeQueryProcessor.getOfSelectedEmployee(input);
	}

	/**
	 * Search list data.
	 *
	 * @param input
	 *            the input
	 * @return the list
	 */
	@POST
	@Path("search")
	public List<EmployeeSearchListData> searchListData(EmployeeSearchListQuery input) {
		return this.employeeQueryProcessor.searchEmployees(input);
	}
	
	/**
	 * Get Employee Info to Display Screen Delete Emp
	 * @param employeeId
	 * @return
	 */
	@POST
	@Path("getemployeetodelete/{employeeId}")
	public EmployeeToDeleteDto getEmployee(@PathParam("employeeId") String employeeId) {
		return employeeFinder.getEmployeeInfoToDelete(employeeId);
	}
	
	@POST
	@Path("deleteemployee")
	public void delereEmployee(EmployeeDeleteCommand command) {
		this.empDeleteHandler.handle(command);
	}
	
	@POST
	@Path("getallemployeetodelete")
	public List<EmployeeToDeleteDto> getAllEmployeeDelete() {
		return employeeFinder.getAllEmployeeInfoToDelete();
	}
	
	@POST
	@Path("getdetailemployeetodelete/{employeeId}")
	public EmployeeToDeleteDetailDto getDetailEmpDelete(@PathParam("employeeId") String employeeId) {
		return employeeFinder.getEmployeeDetailInfoToDelete(employeeId);
	}
	
	@POST
	@Path("restoredata")
	public void restoreData(EmployeeDeleteToRestoreCommand command) {
	}
}
