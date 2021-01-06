package nts.uk.ctx.at.request.app.find.application.holidaywork.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.command.application.common.AppDispInfoStartupCmd;
import nts.uk.ctx.at.request.app.command.application.holidaywork.AppHdWorkDispInfoCmd;
import nts.uk.ctx.at.request.app.command.application.holidaywork.AppHolidayWorkCmd;

/**
 * Refactor5
 * @author huylq
 *
 */
@Data
@NoArgsConstructor
public class AppHolidayWorkParamMobile {

	/**
	 * 画面モード
	 */
	private Boolean mode;
	
	/**
	 * 会社ID
	 */
	private String companyId;
	
	/**
	 * 社員ID＜Optional＞
	 */
	private String employeeId;
	
	/**
	 * 申請対象日リスト＜Optional＞
	 */
	private String appDate;
	
	/**
	 * 休日出勤申請起動時の表示情報＜Optional＞
	 */
	private AppHdWorkDispInfoCmd appHdWorkDispInfo;
	
	/**
	 * 休日出勤申請＜Optional＞
	 */
	private AppHolidayWorkCmd appHolidayWork;
	
	private AppDispInfoStartupCmd appDispInfoStartupOutput;
}
