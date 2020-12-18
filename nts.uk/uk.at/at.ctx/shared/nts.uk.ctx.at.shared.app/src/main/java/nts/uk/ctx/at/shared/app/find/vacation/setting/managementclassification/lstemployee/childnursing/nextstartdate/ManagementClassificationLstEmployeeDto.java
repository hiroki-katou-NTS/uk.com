package nts.uk.ctx.at.shared.app.find.vacation.setting.managementclassification.lstemployee.childnursing.nextstartdate;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import nts.uk.ctx.at.shared.app.command.vacation.setting.nursingleave.dto.NursingLeaveSettingDto;

@Data
@Builder
public class ManagementClassificationLstEmployeeDto  {
	/**
	 * 管理区分
	 */
	private NursingLeaveSettingDto managementClassification;
	
	/**
	 * List<社員情報>
	 */
	private List<EmployeeInfoBasic> lstEmployee;
	
	/**
	 * 次回起算日
	 */
	private String nextStartDate;
}
