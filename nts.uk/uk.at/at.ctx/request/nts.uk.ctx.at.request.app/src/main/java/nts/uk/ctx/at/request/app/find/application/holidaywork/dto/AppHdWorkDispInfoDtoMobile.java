package nts.uk.ctx.at.request.app.find.application.holidaywork.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.AppHdWorkDispInfoOutputMobile;

/**
 * Refactor5
 * @author huylq
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppHdWorkDispInfoDtoMobile {

	/**
	 * 休日出勤申請起動時の表示情報
	 */
	private AppHdWorkDispInfoDto appHdWorkDispInfo;
	
	/**
	 * 休日出勤申請
	 */
	private AppHolidayWorkDto appHolidayWork;
	
	public static AppHdWorkDispInfoDtoMobile fromDomain(AppHdWorkDispInfoOutputMobile domain) {
		if(domain == null) return null;
		return new AppHdWorkDispInfoDtoMobile(
				AppHdWorkDispInfoDto.fromDomain(domain.getAppHdWorkDispInfo()),
				domain.getAppHolidayWork().isPresent() ? AppHolidayWorkDto.fromDomain(domain.getAppHolidayWork().get()) : null);
	}
}
