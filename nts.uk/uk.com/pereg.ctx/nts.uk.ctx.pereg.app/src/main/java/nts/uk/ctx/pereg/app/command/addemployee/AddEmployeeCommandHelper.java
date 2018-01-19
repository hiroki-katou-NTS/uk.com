package nts.uk.ctx.pereg.app.command.addemployee;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.gul.collection.CollectionUtil;
import nts.gul.security.hash.password.PasswordHash;
import nts.uk.ctx.bs.employee.dom.empfilemanagement.EmpFileManagementRepository;
import nts.uk.ctx.bs.employee.dom.empfilemanagement.PersonFileManagement;
import nts.uk.ctx.bs.employee.dom.empfilemanagement.TypeFile;
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
import nts.uk.ctx.bs.person.dom.person.info.BloodType;
import nts.uk.ctx.bs.person.dom.person.info.GenderPerson;
import nts.uk.ctx.bs.person.dom.person.info.Person;
import nts.uk.ctx.bs.person.dom.person.info.PersonRepository;
import nts.uk.ctx.pereg.dom.reghistory.EmpRegHistory;
import nts.uk.ctx.pereg.dom.reghistory.EmpRegHistoryRepository;
import nts.uk.ctx.sys.auth.dom.user.User;
import nts.uk.ctx.sys.auth.dom.user.UserRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class AddEmployeeCommandHelper {

	@Inject
	private UserRepository userRepository;

	@Inject
	private EmpFileManagementRepository perFileManagementRepository;

	@Inject
	private EmpRegHistoryRepository empHisRepo;

	@Inject
	private AffCompanyHistRepository companyHistRepo;

	@Inject
	private AffCompanyInfoRepository companyInfoRepo;

	@Inject
	private EmployeeDataMngInfoRepository empDataRepo;

	@Inject
	private PersonRepository personRepo;

	/** The workplace history repository. */
	// @Inject
	// private AffWorkplaceHistoryRepository workplaceHistRepo;
	//
	// @Inject
	// private WorkplaceInfoRepository workPlaceInfoRepo;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void addBasicData(AddEmployeeCommand command, String personId, String employeeId, String comHistId,
			String companyId, String userId) {

		// add newPerson

		addNewPerson(personId, command);

		// addmngInfo

		addEmployeeDataMngInfo(personId, employeeId, command, companyId);

		// add AffCompanyHist

		addAffCompanyHist(personId, employeeId, command, companyId, comHistId);

		// add new User
		addNewUser(personId, command, userId);

		// register avatar

		addAvatar(personId, command);

		// for test
		// addAffHist(companyId, employeeId);

		// Update employee registration history
		updateEmployeeRegHist(companyId, employeeId);
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

	private void addNewPerson(String personId, AddEmployeeCommand command) {
		Person newPerson = Person.createFromJavaType(ConstantUtils.minDate(), BloodType.Unselected.value,
				GenderPerson.Male.value, personId, " ", "", command.getEmployeeName(), " ", "", "", "", "", "", "", "",
				"", "", "");

		this.personRepo.addNewPerson(newPerson);

	}

	private void addEmployeeDataMngInfo(String personId, String employeeId, AddEmployeeCommand command,
			String companyId) {
		// check duplicate employeeCode
		List<EmployeeDataMngInfo> infoList = this.empDataRepo
				.getEmployeeNotDeleteInCompany(AppContexts.user().companyId(), command.getEmployeeCode());

		if (!CollectionUtil.isEmpty(infoList)) {
			throw new BusinessException("Msg_345");
		}
		// add system data
		this.empDataRepo.add(EmployeeDataMngInfo.createFromJavaType(companyId, personId, employeeId,
				command.getEmployeeCode(), EmployeeDeletionAttr.NOTDELETED.value, null, "", ""));

	}

	private void addAffCompanyHist(String personId, String employeeId, AddEmployeeCommand command, String companyId,
			String comHistId) {
		List<AffCompanyHistByEmployee> comHistList = new ArrayList<AffCompanyHistByEmployee>();

		List<AffCompanyHistItem> comHistItemList = new ArrayList<AffCompanyHistItem>();

		comHistItemList.add(new AffCompanyHistItem(comHistId, false,
				new DatePeriod(command.getHireDate(), ConstantUtils.maxDate())));

		comHistList.add(new AffCompanyHistByEmployee(employeeId, comHistItemList));

		AffCompanyHist newComHist = new AffCompanyHist(personId, comHistList);

		this.companyHistRepo.add(newComHist);

		AffCompanyInfo newComInfo = AffCompanyInfo.createFromJavaType(comHistId, " ", ConstantUtils.maxDate(),
				ConstantUtils.maxDate());

		this.companyInfoRepo.add(newComInfo);

	}

	private void addAvatar(String personId, AddEmployeeCommand command) {
		if (command.getAvatarId() != "") {
			PersonFileManagement perFile = PersonFileManagement.createFromJavaType(personId, command.getAvatarId(),
					TypeFile.AVATAR_FILE.value, null);

			this.perFileManagementRepository.insert(perFile);
		}

	}

	private void updateEmployeeRegHist(String companyId, String employeeId) {

		String currentEmpId = AppContexts.user().employeeId();

		Optional<EmpRegHistory> optRegHist = this.empHisRepo.getLastRegHistory(currentEmpId);

		EmpRegHistory newEmpRegHistory = EmpRegHistory.createFromJavaType(currentEmpId, companyId,
				GeneralDateTime.now(), employeeId, "");

		if (optRegHist.isPresent()) {

			this.empHisRepo.update(newEmpRegHistory);

		} else {

			this.empHisRepo.add(newEmpRegHistory);

		}

	}

	private void addNewUser(String personId, AddEmployeeCommand command, String userId) {
		// add new user
		String passwordHash = PasswordHash.generate(command.getPassword(), userId);
		User newUser = User.createFromJavatype(userId, false, passwordHash, command.getLoginId(),
				AppContexts.user().contractCode(), GeneralDate.fromString("9999/12/31", "yyyy/MM/dd"), 0, 0, "",
				command.getEmployeeName(), personId);

		this.userRepository.addNewUser(newUser);

	}

}
