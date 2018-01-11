package nts.uk.ctx.pereg.app.find.reghistory;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfo;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfoRepository;
import nts.uk.ctx.bs.person.dom.person.info.Person;
import nts.uk.ctx.bs.person.dom.person.info.PersonRepository;
import nts.uk.ctx.pereg.dom.reghistory.EmpRegHistoryRepository;
import nts.uk.ctx.pereg.dom.reghistory.LastEmRegHistory;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class EmpRegHistoryFinder {

	@Inject
	private EmpRegHistoryRepository empHisRepo;

	@Inject
	private EmployeeDataMngInfoRepository empDataMngRepo;

	@Inject
	private PersonRepository personRepo;

	public EmpRegHistoryDto getLastRegHistory() {
		String empId = AppContexts.user().employeeId();
		String companyId = AppContexts.user().companyId();
		Optional<LastEmRegHistory> opt = this.empHisRepo.getLastRegHistory(empId, companyId);
		if (!opt.isPresent()) {

			return null;

		} else {

			EmpRegHistoryDto regHistDto = EmpRegHistoryDto.createFromDomain(opt.get());

			boolean setEmpNameRes = regHistDto.getLastRegEmployee() != null
					? setLastRegName(regHistDto.getLastRegEmployee()) : false;

			boolean setComNameRes = false;
			if (regHistDto.getLastRegEmployeeOfCompany() != null) {
				if (regHistDto.getLastRegEmployee() != null) {

					if (regHistDto.getLastRegEmployee().EmployeeID != regHistDto
							.getLastRegEmployeeOfCompany().EmployeeID) {

						setComNameRes = setLastRegName(regHistDto.getLastRegEmployeeOfCompany());
					} else {

						regHistDto.setLastRegEmployeeOfCompany(null);
					}
				} else {
					setComNameRes = setLastRegName(regHistDto.getLastRegEmployeeOfCompany());

				}
			}

			if (!setEmpNameRes && !setComNameRes) {

				return null;
			}

			return regHistDto;
		}
	}

	private boolean setLastRegName(RegEmployeeDto regEmpDto) {
		if (regEmpDto == null) {
			return false;
		}
		Optional<EmployeeDataMngInfo> dataOpt = this.empDataMngRepo.findByEmployeeId(regEmpDto.getEmployeeID()).stream()
				.findFirst();

		if (!dataOpt.isPresent()) {
			return false;

		}
		Optional<Person> personOpt = this.personRepo.getByPersonId(dataOpt.get().getPersonId());
		if (!personOpt.isPresent()) {
			return false;
		}
		Person person = personOpt.get();

		String businessName = person.getPersonNameGroup().getBusinessName().v();

		String PersonName = person.getPersonNameGroup().getPersonName().getFullName().v();

		regEmpDto.setEmployeeName(!businessName.equals("") ? businessName : PersonName);

		return true;

	}
}
