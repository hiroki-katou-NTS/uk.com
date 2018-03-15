package nts.uk.ctx.pereg.app.command.addemployee;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHist;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHistByEmployee;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHistItem;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHistRepository;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyInfo;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyInfoRepository;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfo;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfoRepository;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDeletionAttr;
import nts.uk.ctx.bs.person.dom.person.common.ConstantUtils;
import nts.uk.ctx.bs.person.dom.person.info.GenderPerson;
import nts.uk.ctx.bs.person.dom.person.info.Person;
import nts.uk.ctx.bs.person.dom.person.info.PersonRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class AddEmployeeCommandHelper {

	@Inject
	private AffCompanyHistRepository companyHistRepo;

	@Inject
	private AffCompanyInfoRepository companyInfoRepo;

	@Inject
	private EmployeeDataMngInfoRepository empDataRepo;

	@Inject
	private PersonRepository personRepo;

	public void addBasicData(AddEmployeeCommand command, String personId, String employeeId, String comHistId,
			String companyId) {

		// add newPerson

		addNewPerson(personId, command.getEmployeeName());

		// addmngInfo

		addEmployeeDataMngInfo(personId, employeeId, command.getEmployeeCode(), companyId);

		// add AffCompanyHist

		addAffCompanyHist(personId, employeeId, command.getHireDate(), companyId, comHistId);

	}

	// private void addAffHist(String companyId, String employeeId) {
	// List<WorkplaceInfo> wplst = this.workPlaceInfoRepo.findAll(companyId,
	// GeneralDate.today());
	// Random rnd = new Random();
	// WorkplaceInfo wp = wplst.get(rnd.nextInt(wplst.size()));
	// AffWorkplaceHistory newAffWork =
	// AffWorkplaceHistory.createFromJavaType(wp.getWorkplaceId(),
	// ConstantUtils.minDate(), ConstantUtils.maxDate(), employeeId);
	// this.workplaceHistRepo.addAffWorkplaceHistory(newAffWork);
	//
	// }

	private void addNewPerson(String personId, String employeeName) {
		Person newPerson = Person.createFromJavaType(ConstantUtils.minDate(), null, GenderPerson.Male.value, personId,
				" ", "", employeeName, " ", "", "", "", "", "", "", "", "", "", "");

		this.personRepo.addNewPerson(newPerson);

	}

	private void addEmployeeDataMngInfo(String personId, String employeeId, String employeeCode, String companyId) {
		// check duplicate employeeCode
		Optional<EmployeeDataMngInfo> empInfo = this.empDataRepo.findByEmployeCD(employeeCode,
				AppContexts.user().companyId());

		if (empInfo.isPresent()) {
			throw new BusinessException("Msg_345");
		}
		
		// add system data
		this.empDataRepo.add(EmployeeDataMngInfo.createFromJavaType(companyId, personId, employeeId, employeeCode,
				EmployeeDeletionAttr.NOTDELETED.value, null, "", ""));

	}

	private void addAffCompanyHist(String personId, String employeeId, GeneralDate hireDate, String companyId,
			String comHistId) {
		List<AffCompanyHistByEmployee> comHistList = new ArrayList<>();
		List<AffCompanyHistItem> comHistItemList = new ArrayList<>();

		comHistItemList.add(new AffCompanyHistItem(comHistId, false, new DatePeriod(hireDate, GeneralDate.max())));
		comHistList.add(new AffCompanyHistByEmployee(employeeId, comHistItemList));
		AffCompanyHist newComHist = new AffCompanyHist(personId, comHistList);
		this.companyHistRepo.add(newComHist);

		AffCompanyInfo newComInfo = AffCompanyInfo.createFromJavaType(comHistId, " ", null, null);
		this.companyInfoRepo.add(newComInfo);

	}

}
