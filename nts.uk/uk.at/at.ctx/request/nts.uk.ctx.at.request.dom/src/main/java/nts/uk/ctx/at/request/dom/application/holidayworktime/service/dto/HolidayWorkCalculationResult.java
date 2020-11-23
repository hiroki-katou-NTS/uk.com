package nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.overtime.ApplicationTime;
import nts.uk.ctx.at.request.dom.application.overtime.OverStateOutput;

/**
 * 休日出勤の計算結果(従業員)
 * @author huylq
 *Refactor5
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HolidayWorkCalculationResult {

	/**
	 * 事前申請・実績の超過状態
	 */
	private OverStateOutput actualOvertimeStatus;
	
	/**
	 * 申請時間
	 */
	private ApplicationTime applicationTime;
	
	/**
	 * 計算済フラグ
	 */
	private CalculatedFlag calculatedFlag;
}
