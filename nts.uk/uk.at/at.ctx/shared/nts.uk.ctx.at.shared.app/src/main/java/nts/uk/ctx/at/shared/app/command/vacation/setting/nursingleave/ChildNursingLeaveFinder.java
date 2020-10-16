package nts.uk.ctx.at.shared.app.command.vacation.setting.nursingleave;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.app.command.vacation.setting.nursingleave.dto.NursingLeaveSettingDto;
import nts.uk.ctx.at.shared.app.find.holidaysetting.employee.ManagementClassificationByEmployeeDto;
import nts.uk.ctx.at.shared.app.find.remainingnumber.subhdmana.dto.EmployeeBasicInfoDto;
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
	
	public ManagementClassificationByEmployeeDto startPage(List<String> sIDs, GeneralDate baseDate) {
		String cId= AppContexts.user().companyId();
		List<EmployeeImport> lstEmp = empEmployeeAdapter.findByEmpId(sIDs);
		List<EmployeeBasicInfoDto> lstEmpRs =  lstEmp.stream().map(item -> EmployeeBasicInfoDto
						.builder()
						.employeeCode(item.getEmployeeCode())
						.employeeId(item.getEmployeeId())
						.employeeName(item.getEmployeeName())
						.build()
		).collect(Collectors.toList());
		NursingLeaveSetting childNursingLeave = nursingLeaveRepo.findByCompanyIdAndNursingCategory(cId, NursingCategory.ChildNursing.value);
		if(childNursingLeave == null) {
			throw new BusinessException("Msg_1962");
		}
		NursingLeaveSettingDto childNursingLeaveDt = NursingLeaveSettingDto.builder()
				.manageType(childNursingLeave.getManageType().value)
				.nursingCategory(childNursingLeave.getNursingCategory().value)
				.startMonthDay(childNursingLeave.getStartMonthDay() !=null ? childNursingLeave.getStartMonthDay() : null)
				.nursingNumberLeaveDay(childNursingLeave.getMaxPersonSetting().getNursingNumberLeaveDay().v())
				.nursingNumberPerson(childNursingLeave.getMaxPersonSetting().getNursingNumberPerson().v())
				.specialHolidayFrame(childNursingLeave.getSpecialHolidayFrame().orElse(0))
				.absenceWork(childNursingLeave.getWorkAbsence().orElse(0))
				.build();
		return ManagementClassificationByEmployeeDto.builder()
		.lstEmp(lstEmpRs)
		.nursingLeaveSt(childNursingLeaveDt)
		.nextStartMonthDay(childNursingLeave.getNextStartMonthDay(baseDate).toString())
		.build();
	}
}

