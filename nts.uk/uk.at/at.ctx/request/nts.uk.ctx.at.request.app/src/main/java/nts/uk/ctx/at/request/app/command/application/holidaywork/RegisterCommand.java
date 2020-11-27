package nts.uk.ctx.at.request.app.command.application.holidaywork;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.command.application.overtime.AppOverTimeCommand;
import nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.applicationsetting.apptypeset.AppTypeSettingCommand;
import nts.uk.ctx.at.request.app.find.application.common.dto.ApprovalPhaseStateForAppDto;

/**
 * Refactor5
 * @author huylq
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterCommand {
	/**
	 * 会社ID
	 */
	private String companyId;
	
	/**
	 * 休日出勤申請
	 */
	private AppHolidayWorkCmd appHolidayWork;
	
	/**
	 * 休日出勤申請起動時の表示情報
	 */
	private AppHdWorkDispInfoCmd appHdWorkDispInfo;
	
	/**
	 * 承認ルートインスタンス
	 */
	public List<ApprovalPhaseStateForAppDto> approvalPhaseState;
}
