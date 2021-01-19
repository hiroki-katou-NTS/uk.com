package nts.uk.ctx.at.request.app.find.application.holidaywork.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import nts.uk.ctx.at.request.app.command.application.holidaywork.AppHdWorkDispInfoCmd;

/**
 * Refactor5
 * @author huylq
 *
 */
@Data
@NoArgsConstructor
public class ParamHolidayWorkChangeDateMobile {
	
	/**
	 * 会社ID
	 */
	private String companyId;
	
	/**
	 * 申請日
	 */
	private String appDate;
	
	/**
	 * 休日出勤申請起動時の表示情報
	 */
	private AppHdWorkDispInfoCmd appHdWorkDispInfo;
}
