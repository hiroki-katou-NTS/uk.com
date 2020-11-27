package nts.uk.ctx.at.request.app.find.application.holidaywork.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.application.overtime.ApplicationTimeDto;
import nts.uk.ctx.at.request.app.find.application.overtime.OverStateOutputDto;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.CalculatedFlag;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.HolidayWorkCalculationResult;

/**
 * Refactor5
 * @author huylq
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class HolidayWorkCalculationResultDto {

	/**
	 * 事前申請・実績の超過状態
	 */
	private OverStateOutputDto actualOvertimeStatus;
	
	/**
	 * 申請時間
	 */
	private ApplicationTimeDto applicationTime;
	
	/**
	 * 計算済フラグ
	 */
	private boolean calculatedFlag;
	
	public static HolidayWorkCalculationResultDto fromDomain(HolidayWorkCalculationResult domain) {
		if(domain == null) return null;
		return new HolidayWorkCalculationResultDto(OverStateOutputDto.fromDomain(domain.getActualOvertimeStatus()), 
				ApplicationTimeDto.fromDomain(domain.getApplicationTime()), domain.getCalculatedFlag().equals(CalculatedFlag.CALCULATED));
	}
}
