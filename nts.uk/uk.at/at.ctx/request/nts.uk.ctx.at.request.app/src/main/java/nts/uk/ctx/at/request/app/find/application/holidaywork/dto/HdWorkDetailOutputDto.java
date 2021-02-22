package nts.uk.ctx.at.request.app.find.application.holidaywork.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.HdWorkDetailOutput;

/**
 * Refactor5
 * @author huylq
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HdWorkDetailOutputDto {
	
	/**
	 * 休日出勤申請起動時の表示情報
	 */
	private AppHdWorkDispInfoDto appHdWorkDispInfoOutput;
	
	/**
	 * 休日出勤申請
	 */
	private AppHolidayWorkDto appHolidayWork;
	
	public static HdWorkDetailOutputDto fromDomain(HdWorkDetailOutput domain) {
		if(domain == null) return null;
		return new HdWorkDetailOutputDto(AppHdWorkDispInfoDto.fromDomain(domain.getAppHdWorkDispInfoOutput()), 
				AppHolidayWorkDto.fromDomain(domain.getAppHolidayWork()));
	}
}
