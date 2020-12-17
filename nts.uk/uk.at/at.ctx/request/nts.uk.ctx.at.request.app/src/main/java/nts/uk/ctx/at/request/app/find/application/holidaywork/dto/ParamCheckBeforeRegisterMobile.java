package nts.uk.ctx.at.request.app.find.application.holidaywork.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.command.application.holidaywork.AppHdWorkDispInfoCmd;
import nts.uk.ctx.at.request.app.command.application.holidaywork.AppHolidayWorkInsertCmd;

/**
 * Refactor5
 * @author huylq
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ParamCheckBeforeRegisterMobile {
	
	/**
	 * require
	 */
	private boolean require;
	
	/**
	 * 画面モード
	 */
	private Boolean mode;
	
	/**
	 * 会社ID
	 */
	private String companyId;
	
	/**
	 * 休日出勤申請起動時の表示情報
	 */
	private AppHdWorkDispInfoCmd appHdWorkDispInfo;
	
	/**
	 * 休日出勤申請
	 */
	private AppHolidayWorkInsertCmd appHolidayWork;
}
