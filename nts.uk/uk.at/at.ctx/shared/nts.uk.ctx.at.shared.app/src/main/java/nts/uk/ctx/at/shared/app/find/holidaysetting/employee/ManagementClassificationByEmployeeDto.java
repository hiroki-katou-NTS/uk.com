package nts.uk.ctx.at.shared.app.find.holidaysetting.employee;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import nts.uk.ctx.at.shared.app.command.vacation.setting.nursingleave.dto.NursingLeaveSettingDto;
import nts.uk.ctx.at.shared.app.find.remainingnumber.subhdmana.dto.EmployeeBasicInfoDto;

@Data
@Builder
public class ManagementClassificationByEmployeeDto {
	private List<EmployeeBasicInfoDto> lstEmp;
	private NursingLeaveSettingDto nursingLeaveSt;
}
