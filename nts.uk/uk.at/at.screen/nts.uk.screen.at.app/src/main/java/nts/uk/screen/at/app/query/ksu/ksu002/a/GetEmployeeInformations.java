package nts.uk.screen.at.app.query.ksu.ksu002.a;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmployeeInformationAdapter;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmployeeInformationImport;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmployeeInformationQueryDtoImport;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.ScWorkplaceAdapter;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.dto.AffWorkplaceHistoryItem;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.EmployeeSearchCallSystemType;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.GetEmpCanReferService;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.RegulationInfoEmpQuery;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter.WorkplaceGroupAdapter;
import nts.uk.query.pub.employee.EmployeeSearchQueryDto;
import nts.uk.query.pub.employee.RegulationInfoEmployeeExport;
import nts.uk.query.pub.employee.RegulationInfoEmployeePub;
import nts.uk.screen.at.app.query.ksu.ksu002.a.dto.EmployeeInfoDto;
import nts.uk.screen.at.app.query.ksu.ksu002.a.dto.GetEmployeeInformationsDto;
import nts.uk.shr.com.context.AppContexts;

/**
 * DS: 社員情報リストを取得する
 * UKDesign.UniversalK.就業.KSU_スケジュール.KSU002_個人スケジュール修正(個人別).A:メイン画面.メニュー別OCD.社員情報リストを取得する
 * 
 * @author chungnt
 *
 */

@Stateless
public class GetEmployeeInformations {

	@Inject
	private WorkplaceGroupAdapter workplaceGroupAdapter;

	@Inject
	private RegulationInfoEmployeePub regulInfoEmpPub;

	@Inject
	private EmployeeInformationAdapter empInfoAdapter;
	
	@Inject
	private ScWorkplaceAdapter syWorkplaceAdapter;

	final static String SPACE = " ";
	final static String ZEZO_TIME = "00:00";
	final static String DATE_TIME_FORMAT = "yyyy/MM/dd HH:mm";

	public GetEmployeeInformationsDto getEmployeeInformations() {
		GetEmployeeInformationsDto result = new GetEmployeeInformationsDto();
		GeneralDate today = GeneralDate.today();
		String sidLogin = AppContexts.user().employeeId();
		DatePeriod datePeriod = new DatePeriod(today, today);

		// 1. [No.650]社員が所属している職場を取得する
		AffWorkplaceHistoryItem workPlaceInfo = syWorkplaceAdapter.getAffWkpHistItemByEmpDate(sidLogin, today);
		
		// 2. 職場を指定して識別情報を作成する(職場ID)
		TargetOrgIdenInfor targetOrgIdenInfor = TargetOrgIdenInfor.creatIdentifiWorkplace(workPlaceInfo.getWorkplaceId());

		// 3. get domainSv 組織を指定して参照可能な社員を取得する
		RequireGetEmpImpl requireGetEmpImpl = new RequireGetEmpImpl();
		// danh sach nhân viên thuộc trực thuộc workplace
		List<String> listSidByOrg = GetEmpCanReferService.getByOrg(requireGetEmpImpl, sidLogin, today, datePeriod,
				targetOrgIdenInfor);

		// 4. create 取得したい社員情報
		EmployeeInformationQueryDtoImport input = new EmployeeInformationQueryDtoImport(listSidByOrg, today, false,
				false, false, false, false, false);

		// 5. <<Public>> 社員の情報を取得する
		List<EmployeeInformationImport> listEmployeeInformation = empInfoAdapter.getEmployeeInfo(input);

		listEmployeeInformation.stream().forEach(f -> {
			result.employeeInfos.add(new EmployeeInfoDto(f.getEmployeeId(), f.getEmployeeCode(), f.getBusinessName()));
		});

		return result;
	}

	@AllArgsConstructor
	private class RequireGetEmpImpl implements GetEmpCanReferService.Require {

		@Override
		public List<String> getEmpCanReferByWorkplaceGroup(String empId, GeneralDate date, DatePeriod period,
				String workplaceGroupID) {
			List<String> data = workplaceGroupAdapter.getReferableEmp(empId, date, period, workplaceGroupID);
			return data;
		}

		@Override
		public List<String> getAllEmpCanReferByWorkplaceGroup(String empId, GeneralDate date, DatePeriod period) {
			// don't have to implement it
			return null;
		}

		@Override
		public List<String> sortEmployee(List<String> employeeIdList, EmployeeSearchCallSystemType systemType,
				Integer sortOrderNo, GeneralDate date, Integer nameType) {
			List<String> data = regulInfoEmpPub.sortEmployee(AppContexts.user().companyId(), employeeIdList,
					systemType.value, sortOrderNo, nameType,
					GeneralDateTime.fromString(date.toString() + SPACE + ZEZO_TIME, DATE_TIME_FORMAT));
			return data;
		}

		@Override
		public String getRoleID() {

			return AppContexts.user().roles().forAttendance();
		}

		@Override
		public List<String> searchEmployee(RegulationInfoEmpQuery q, String roleId) {
			EmployeeSearchQueryDto query = EmployeeSearchQueryDto.builder()
					.baseDate(GeneralDateTime.fromString(q.getBaseDate().toString() + SPACE + ZEZO_TIME,
							DATE_TIME_FORMAT))
					.referenceRange(q.getReferenceRange().value).systemType(q.getSystemType().value)
					.filterByWorkplace(q.getFilterByWorkplace()).workplaceCodes(q.getWorkplaceIds())
					.filterByEmployment(false).employmentCodes(new ArrayList<String>()).filterByDepartment(false)
					.departmentCodes(new ArrayList<String>()).filterByClassification(false)
					.classificationCodes(new ArrayList<String>()).filterByJobTitle(false)
					.jobTitleCodes(new ArrayList<String>()).filterByWorktype(false)
					.worktypeCodes(new ArrayList<String>()).filterByClosure(false).closureIds(new ArrayList<Integer>())
					.periodStart(GeneralDateTime.fromString(q.getPeriodStart() + SPACE + ZEZO_TIME, DATE_TIME_FORMAT))
					.periodEnd(GeneralDateTime.fromString(q.getPeriodEnd() + SPACE + ZEZO_TIME, DATE_TIME_FORMAT))
					.includeIncumbents(true).includeWorkersOnLeave(true).includeOccupancy(true).includeRetirees(false)
					.includeAreOnLoan(false).includeGoingOnLoan(false).retireStart(GeneralDateTime.now())
					.retireEnd(GeneralDateTime.now()).sortOrderNo(null).nameType(null)

					.build();
			List<RegulationInfoEmployeeExport> data = regulInfoEmpPub.find(query);
			List<String> resultList = data.stream().map(item -> item.getEmployeeId()).collect(Collectors.toList());
			return resultList;
		}

	}
}
