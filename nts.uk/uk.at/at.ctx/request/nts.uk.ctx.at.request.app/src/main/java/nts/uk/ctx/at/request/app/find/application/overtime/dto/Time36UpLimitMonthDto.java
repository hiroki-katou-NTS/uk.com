package nts.uk.ctx.at.request.app.find.application.overtime.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Time36UpLimitMonthDto {
	
	/*
	 * 期間
	 */
	public Integer periodYearStart;
	public Integer periodYearEnd;
	
	/*
	 * 平均時間
	 */
	public Integer averageTime;
	
	/*
	 * 合計時間
	 */
	public Integer totalTime;
}
