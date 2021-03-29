package nts.uk.screen.at.app.query.ksu.ksu002.a;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfo;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfoRepository;
import nts.uk.ctx.bs.person.dom.person.info.Person;
import nts.uk.ctx.bs.person.dom.person.info.PersonRepository;
import nts.uk.screen.at.app.query.ksu.ksu002.a.dto.EmployeeInformationDto;
import nts.uk.shr.com.context.AppContexts;

/**
 * 社員情報を取得する
 * UKDesign.UniversalK.就業.KSU_スケジュール.KSU002_個人スケジュール修正(個人別).A:メイン画面.メニュー別OCD.社員情報を取得する
 * 
 * @author chungnt
 *
 */

@Stateless
public class EmployeeInformation {

	@Inject
	private EmployeeDataMngInfoRepository employeeDataMngInfoRepo;

	@Inject
	private PersonRepository personRepo;

//	@Inject
//	private AffWorkplaceHistoryItemRepository affWorkplaceHistoryItemRepo;

	public EmployeeInformationDto getEmployeeInfo() {

		Optional<EmployeeDataMngInfo> employeeDataMngInfo = employeeDataMngInfoRepo.findByEmpId(AppContexts.user().employeeId());
		EmployeeInformationDto dto = new EmployeeInformationDto();

		if (employeeDataMngInfo.isPresent()) {
			dto.setEmployeeCd(employeeDataMngInfo.get().getEmployeeCode().v());

			Optional<Person> person = personRepo.getByPersonId(employeeDataMngInfo.get().getPersonId());

			if (person.isPresent()) {
				dto.setEmployeeName(person.get().getPersonNameGroup().getBusinessName().v());
			}
		}

//		List<AffWorkplaceHistoryItem> affWorkplaceHistoryItem = affWorkplaceHistoryItemRepo
//				.getAffWrkplaHistItemByEmpIdAndDate(input.getBaseDate(), input.getEmployeeId());
//
//		if (!affWorkplaceHistoryItem.isEmpty()) {
//			dto.setWorkplaceId(affWorkplaceHistoryItem.get(0).getWorkplaceId());
//		}

		return dto;
	}
}
