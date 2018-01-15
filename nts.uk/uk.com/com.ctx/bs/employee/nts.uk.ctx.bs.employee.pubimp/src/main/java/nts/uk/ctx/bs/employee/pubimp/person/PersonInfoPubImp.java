package nts.uk.ctx.bs.employee.pubimp.person;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHist;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHistByEmployee;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHistItem;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHistRepository;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfo;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfoRepository;
import nts.uk.ctx.bs.employee.pub.person.IPersonInfoPub;
import nts.uk.ctx.bs.employee.pub.person.PersonInfoExport;
import nts.uk.ctx.bs.person.dom.person.info.Person;
import nts.uk.ctx.bs.person.dom.person.info.PersonRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class PersonInfoPubImp implements IPersonInfoPub {

	@Inject
	private EmployeeDataMngInfoRepository empDataMngRepo;

	@Inject
	private PersonRepository personRepo;

	@Inject
	private AffCompanyHistRepository affComHistRepo;

	@Override
	public PersonInfoExport getPersonInfo(String employeeId) {
		Optional<EmployeeDataMngInfo> empOpt = empDataMngRepo.findByEmpId(employeeId);
		PersonInfoExport perResult = null;

		if (empOpt.isPresent()) {
			EmployeeDataMngInfo empData = empOpt.get();
			perResult = new PersonInfoExport();

			setEmployeeInfo(empData, perResult);

			setPersonInfo(empData.getPersonId(), perResult);

		}
		return perResult;
	}

	private void setPersonInfo(String pId, PersonInfoExport perResult) {
		Optional<Person> _person = personRepo.getByPersonId(pId);
		if (_person.isPresent()) {
			Person person = _person.get();
			perResult.setBirthDay(person.getBirthDate());
			perResult.setPid(person.getPersonId());
			perResult.setGender(person.getGender().value);
			perResult.setPname(person.getPersonNameGroup().getPersonName().getFullName() == null ? ""
					: person.getPersonNameGroup().getPersonName().getFullName().v());
		}
	}

	private void setEmployeeInfo(EmployeeDataMngInfo employee, PersonInfoExport perResult) {
		perResult.setEmployeeId(employee.getEmployeeId());
		perResult.setEmployeeCode(employee.getEmployeeCode() == null ? "" : employee.getEmployeeCode().v());
		String cid = AppContexts.user().companyId();

		AffCompanyHist affComHist = affComHistRepo.getAffCompanyHistoryOfEmployeeDesc(cid, employee.getEmployeeId());

		AffCompanyHistByEmployee affComHistByEmp = affComHist.getAffCompanyHistByEmployee(employee.getEmployeeId());

		if (affComHistByEmp != null) {

			AffCompanyHistItem affComHistItem = new AffCompanyHistItem();

			Optional<AffCompanyHistItem> history = affComHistByEmp.getHistory();

			if (history.isPresent()) {
				affComHistItem = history.get();
				perResult.setEntryDate(affComHistItem.start());
				perResult.setRetiredDate(affComHistItem.end());

			}
		}
	}

}
