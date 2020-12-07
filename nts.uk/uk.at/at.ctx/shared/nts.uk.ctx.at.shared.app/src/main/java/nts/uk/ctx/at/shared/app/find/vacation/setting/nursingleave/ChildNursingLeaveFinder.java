package nts.uk.ctx.at.shared.app.find.vacation.setting.nursingleave;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.app.command.vacation.setting.nursingleave.dto.NursingLeaveSettingDto;
import nts.uk.ctx.at.shared.app.find.holidaysetting.employee.ManagementClassificationByEmployeeDto;
import nts.uk.ctx.at.shared.app.find.remainingnumber.subhdmana.dto.EmployeeBasicInfoDto;
import nts.uk.ctx.at.shared.app.find.vacation.setting.managementclassification.lstemployee.childnursing.nextstartdate.EmployeeInfoBasic;
import nts.uk.ctx.at.shared.app.find.vacation.setting.managementclassification.lstemployee.childnursing.nextstartdate.ManagementClassificationLstEmployeeDto;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmpEmployeeAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeImport;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingCategory;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSettingRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ChildNursingLeaveFinder {
	
	@Inject
	private EmpEmployeeAdapter empEmployeeAdapter;
	
	@Inject
	private NursingLeaveSettingRepository nursingLeaveRepo;
	
	/**
	 * UKDesign.UniversalK.就業.KDL_ダイアログ.KDL051_子の看護休暇ダイアログ.アルゴリズム.子の看護休暇ダイアログ起動.子の看護休暇ダイアログ起動
	 * @param sIDs
	 * @param baseDate
	 * @return
	 */
	public ManagementClassificationByEmployeeDto startPage(List<String> sIDs, GeneralDate baseDate) {
		String cId= AppContexts.user().companyId();
		//	社員ID(List)から個人社員基本情報を取得
		List<EmployeeImport> lstEmp = this.empEmployeeAdapter.findByEmpId(sIDs);
		//	Convert data to Dto
		List<EmployeeBasicInfoDto> lstEmpRs =  lstEmp.stream().map(item -> EmployeeBasicInfoDto
						.builder()
						.employeeCode(item.getEmployeeCode())
						.employeeId(item.getEmployeeId())
						.employeeName(item.getEmployeeName())
						.build()
		).collect(Collectors.toList());
		//	ドメインモデル「介護看護休暇設定」の「管理区分」を取得する。
		NursingLeaveSetting childNursingLeave = this.nursingLeaveRepo.findByCompanyIdAndNursingCategory(cId, NursingCategory.ChildNursing.value);
		//	取得したObject＜介護看護休暇設定＞をチェックする。
		if(childNursingLeave == null) {
			return ManagementClassificationByEmployeeDto.builder()
					.lstEmp(lstEmpRs)
					.nursingLeaveSt(null)
					.nextStartMonthDay(null)
					.build();
		}
		// Convert data to Dto
		NursingLeaveSettingDto childNursingLeaveDt = NursingLeaveSettingDto.builder()
				.manageType(childNursingLeave.getManageType().value)
				.nursingCategory(childNursingLeave.getNursingCategory().value)
				.startMonthDay(childNursingLeave.getStartMonthDay() !=null ? childNursingLeave.getStartMonthDay() : null)
				.nursingNumberLeaveDay(childNursingLeave.getMaxPersonSetting().getNursingNumberLeaveDay().v())
				.nursingNumberPerson(childNursingLeave.getMaxPersonSetting().getNursingNumberPerson().v())
				.specialHolidayFrame(childNursingLeave.getSpecialHolidayFrame().orElse(0))
				.absenceWork(childNursingLeave.getWorkAbsence().orElse(0))
				.build();
		//	/取得したデータを返す。
		return ManagementClassificationByEmployeeDto.builder()
		.lstEmp(lstEmpRs)
		.nursingLeaveSt(childNursingLeaveDt)
		.nextStartMonthDay(childNursingLeave.getNextStartMonthDay(baseDate).toString())
		.build();
	}
	
	/**
	 * UKDesign.UniversalK.就業.KDL_ダイアログ.KDL052_介護休暇ダイアログ.アルゴリズム.起動する.起動する
	 * @param sIDs
	 * @param baseDate
	 * @return
	 */
	public ManagementClassificationLstEmployeeDto findByListEmployeeIdAndRef(List<String> sIDs, GeneralDate baseDate) {
		String cId= AppContexts.user().companyId();
		// 社員ID(List)から個人社員基本情報を取得
		List<EmployeeImport> lstEmp = this.empEmployeeAdapter.findByEmpId(sIDs);
		List<EmployeeInfoBasic> lstEmpRs =  lstEmp.stream().map(item -> EmployeeInfoBasic
                 .builder()
                 .employeeCode(item.getEmployeeCode())
                 .employeeId(item.getEmployeeId())
                 .employeeName(item.getEmployeeName())
                 .build()).collect(Collectors.toList());
		 
		// ドメインモデル「介護看護休暇設定」の「管理区分」を取得する。
		NursingLeaveSetting nursingLeave = this.nursingLeaveRepo.findManageDistinctByCompanyIdAndNusingCategory(cId, NursingCategory.Nursing.value);
		if (nursingLeave == null) {
			return ManagementClassificationLstEmployeeDto.builder()
					.managementClassification(null)
					.lstEmployee(lstEmpRs)
					.nextStartDate(null)
					.build();
		}
		NursingLeaveSettingDto data = NursingLeaveSettingDto.builder()
				.manageType(nursingLeave.getManageType().value)
				.nursingCategory(NursingCategory.Nursing.value)
				.startMonthDay(nursingLeave.getStartMonthDay())
				.nursingNumberLeaveDay(nursingLeave.getMaxPersonSetting().getNursingNumberLeaveDay().v())
				.nursingNumberPerson(nursingLeave.getMaxPersonSetting().getNursingNumberPerson().v())
				.specialHolidayFrame(nursingLeave.getSpecialHolidayFrame().orElse(0))
				.absenceWork(nursingLeave.getWorkAbsence().orElse(0))
				.build();
		// アルゴリズム「次回起算日を求める」を呼び出す。
		String nextStartDate = nursingLeave.getNextStartMonthDay(baseDate).toString();
		
		// 取得したデータを返す。
		ManagementClassificationLstEmployeeDto resultDto =  ManagementClassificationLstEmployeeDto.builder()
				.managementClassification(data)
				.lstEmployee(lstEmpRs)
				.nextStartDate(nextStartDate)
				.build();
					
		return resultDto;
	}
}

