/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.app.find.employee;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.app.find.temporaryabsence.TempAbsHisFinder;
import nts.uk.ctx.bs.employee.dom.employeeinfo.Employee;
import nts.uk.ctx.bs.employee.dom.employeeinfo.EmployeeRepository;
import nts.uk.ctx.bs.employee.dom.employeeinfo.service.EmployeeBusiness;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class EmployeeFinder.
 */
@Stateless
public class EmployeeFinder {

	/** The employee repository. */
	@Inject
	private EmployeeRepository employeeRepository;

	@Inject
	private EmployeeBusiness employeeBusiness;

	/**
	 * Gets the person id by employee code.
	 *
	 * @param employeeCode
	 *            the employee code
	 * @return the person id by employee code
	 */
	public Optional<EmployeeDto> getPersonIdByEmployeeCode(String employeeCode, GeneralDate entryDate) {

		// get login user
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		return this.employeeRepository.findByEmployeeCode(companyId, employeeCode, entryDate)
				.map(item -> EmployeeDto.fromDomain(item));
	}

	/**
	 * Gets the list person id by employee code.
	 *
	 * @param listEmployeeCode
	 *            the list employee code
	 * @return the list person id by employee code
	 */
	public List<EmployeeDto> getListPersonIdByEmployeeCode(List<String> listEmployeeCode) {

		// get login user
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();
		return this.employeeRepository.findByListEmployeeCode(companyId, listEmployeeCode).stream()
				.map(item -> EmployeeDto.fromDomain(item)).collect(Collectors.toList());
	}

	/**
	 * Gets the all employee.
	 *
	 * @return the all employee
	 */
	public List<EmployeeDto> getAllEmployee() {

		// get login user
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();
		return this.employeeRepository.findAll(companyId).stream().map(item -> EmployeeDto.fromDomain(item))
				.collect(Collectors.toList());
	}

	/**
	 * get generate employee code
	 * 
	 * @param startLetter
	 * @return
	 */
	public String getGenerateEmplCode(String startLetters) {
		return employeeBusiness.generateEmplCode(startLetters);
	}

	/**
	 * get generate card no
	 * 
	 * @param startLetter
	 * @return
	 */
	public String getGenerateCardNo(String startLetters) {
		return employeeBusiness.generateCardNo(startLetters);
	}

	


	public Optional<EmployeeDto> getInfoById(String employeeId) {
		Optional<Employee> emp = employeeRepository.getBySid(employeeId);

		/*if (emp.isPresent()) {
			return emp.map(m -> {
				// set list temporary absence to employee domain
				m.setListTemporaryAbsence(tAbsFinder.getListBySid(employeeId));

				return EmployeeDto.fromDomain(m);
			});
		}*/

		return Optional.empty();
	}
}