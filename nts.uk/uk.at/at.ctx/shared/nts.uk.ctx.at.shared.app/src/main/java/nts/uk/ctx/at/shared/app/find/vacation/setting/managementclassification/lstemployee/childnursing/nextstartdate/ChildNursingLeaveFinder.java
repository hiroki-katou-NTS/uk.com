package nts.uk.ctx.at.shared.app.find.vacation.setting.managementclassification.lstemployee.childnursing.nextstartdate;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.app.command.vacation.setting.nursingleave.dto.NursingLeaveSettingDto;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmpEmployeeAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeImport;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingCategory;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSettingRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ChildNursingLeaveFinder {
	
	@Inject
	private EmpEmployeeAdapter empEmployeeAdapter;
	
	@Inject
	private NursingLeaveSettingRepository nursingLeaveSettingRepository;
	
	public ManagementClassificationLstEmployeeDto findByListEmployeeIdAndRef(List<String> sIDs, GeneralDate baseDate) {
		String cId= AppContexts.user().companyId();
		// 社員ID(List)から個人社員基本情報を取得
		List<EmployeeImport> lstEmp = empEmployeeAdapter.findByEmpId(sIDs);
		List<EmployeeInfoBasic> lstEmpRs =  lstEmp.stream().map(item -> EmployeeInfoBasic
                 .builder()
                 .employeeCode(item.getEmployeeCode())
                 .employeeId(item.getEmployeeId())
                 .employeeName(item.getEmployeeName())
                 .build()).collect(Collectors.toList());
		 
		// ドメインモデル「介護看護休暇設定」の「管理区分」を取得する。
		NursingLeaveSetting nursingLeave = nursingLeaveSettingRepository.findManageDistinctByCompanyIdAndNusingCategory(cId, NursingCategory.ChildNursing.value);
		if (nursingLeave == null) {
			throw new BusinessException("Msg_1962");
		}
		NursingLeaveSettingDto data = NursingLeaveSettingDto.builder()
				.manageType(nursingLeave.getManageType().value)
				.nursingCategory(NursingCategory.ChildNursing.value)
				.startMonthDay(nursingLeave.getStartMonthDay())
				.nursingNumberLeaveDay(nursingLeave.getMaxPersonSetting().getNursingNumberLeaveDay().v())
				.nursingNumberPerson(nursingLeave.getMaxPersonSetting().getNursingNumberPerson().v())
				.specialHolidayFrame(nursingLeave.getSpecialHolidayFrame().get())
				.absenceWork(nursingLeave.getWorkAbsence().get())
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
