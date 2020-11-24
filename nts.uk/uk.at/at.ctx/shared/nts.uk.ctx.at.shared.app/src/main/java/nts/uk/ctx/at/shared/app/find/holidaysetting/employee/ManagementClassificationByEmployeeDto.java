package nts.uk.ctx.at.shared.app.find.holidaysetting.employee;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import nts.uk.ctx.at.shared.app.command.vacation.setting.nursingleave.dto.NursingLeaveSettingDto;
import nts.uk.ctx.at.shared.app.find.remainingnumber.subhdmana.dto.EmployeeBasicInfoDto;

@Data
@Builder
public class ManagementClassificationByEmployeeDto {
	//	取得したList<社員情報>
	private List<EmployeeBasicInfoDto> lstEmp;
	//	取得した管理区分
	private NursingLeaveSettingDto nursingLeaveSt;
	//	次回起算日
	private String nextStartMonthDay;
}
