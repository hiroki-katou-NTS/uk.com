package nts.uk.ctx.at.request.dom.application.holidayworktime.service;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWork;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.AppHdWorkDispInfoOutput;

/**
 * Refactor5
 * @author huylq
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppHdWorkDispInfoOutputMobile {

	/**
	 * 休日出勤申請起動時の表示情報
	 */
	private AppHdWorkDispInfoOutput appHdWorkDispInfo;
	
	/**
	 * 休日出勤申請
	 */
	private Optional<AppHolidayWork> appHolidayWork = Optional.empty();
}
