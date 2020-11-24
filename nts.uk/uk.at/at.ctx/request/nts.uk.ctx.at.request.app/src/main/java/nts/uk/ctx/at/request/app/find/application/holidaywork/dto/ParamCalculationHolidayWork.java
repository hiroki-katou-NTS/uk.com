package nts.uk.ctx.at.request.app.find.application.holidaywork.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.command.application.overtime.ApplicationTimeCommand;
import nts.uk.ctx.at.request.app.command.application.overtime.WorkContentCommand;
import nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeLeaveAppCommonSetCommand;
import nts.uk.ctx.at.request.app.find.application.overtime.ApplicationTimeDto;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.WorkContentDto;
import nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeLeaveAppCommonSetDto;

/**
 * Refactor5
 * @author huylq
 *
 */
@Data
@NoArgsConstructor
public class ParamCalculationHolidayWork {
	
	/**
	 * 会社ID
	 */
	private String companyId;
	
	/**
	 * 社員ID
	 */
	private String employeeId;
	
	/**
	 * 申請対象日
	 */
	private String date;
	
	/**
	 * 事前事後区分
	 */
	private int prePostAtr;
	
	/**
	 * 残業休出申請共通設定
	 */
	private OvertimeLeaveAppCommonSetCommand overtimeLeaveAppCommonSet;
	
	/**
	 * 事前の申請時間
	 */
	private ApplicationTimeCommand preApplicationTime;
	
	/**
	 * 実績の申請時間
	 */
	private ApplicationTimeCommand actualApplicationTime;
	
	/**
	 * 勤務内容
	 */
	private WorkContentCommand workContent;
}
