package nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWork;

/**
 * Refactor5
 * @author huylq
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class HdWorkDetailOutput {
	
	/**
	 * 休日出勤申請起動時の表示情報
	 */
	private AppHdWorkDispInfoOutput appHdWorkDispInfoOutput;
	
	/**
	 * 休日出勤申請
	 */
	private AppHolidayWork appHolidayWork;
}
