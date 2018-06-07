package nts.uk.ctx.at.function.dom.adapter.periodofspecialleave;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ComplileInPeriodOfSpecialLeaveImported {
	/**
	 * 付与日数
	 */
	private Double grantDate;
	
	/**
	 * 月初残日数
	 */
	private Double firstMonthRemNumDays;
	
	/**
	 * 使用数日数
	 */
	private Double usedDate;
	
	/**
	 * 残数日数
	 */
	private Double remainDate;
	
	/**
	 * 付与時間
	 */
	private Double grantHours;
	
	/**
	 * 月初残時間
	 */
	private Double firstMonthRemNumHours;
	
	/**
	 * 使用数時間
	 */
	private Double usedHours;
	
	/**
	 * 残数時間
	 */
	private Double remainHours;
}
