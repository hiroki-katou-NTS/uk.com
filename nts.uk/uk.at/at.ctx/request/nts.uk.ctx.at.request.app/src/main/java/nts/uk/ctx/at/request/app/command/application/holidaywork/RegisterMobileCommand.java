package nts.uk.ctx.at.request.app.command.application.holidaywork;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.applicationsetting.apptypeset.AppTypeSettingCommand;

/**
 * Refactor5
 * @author huylq
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterMobileCommand {
	
	/**
	 * 画面モード
	 */
	public Boolean mode;
	
	/**
	 * 会社ID
	 */
	private String companyId;
	
	/**
	 * 休日出勤申請
	 */
	private AppHolidayWorkInsertCmd appHolidayWorkInsert;
	private AppHolidayWorkUpdateCmd appHolidayWorkUpdate;
	
	/**
	 * 申請種類別設定
	 */
	private AppTypeSettingCommand appTypeSetting;
	
	/**
	 * 休日出勤申請起動時の表示情報
	 */
	private AppHdWorkDispInfoCmd appHdWorkDispInfo;
}
