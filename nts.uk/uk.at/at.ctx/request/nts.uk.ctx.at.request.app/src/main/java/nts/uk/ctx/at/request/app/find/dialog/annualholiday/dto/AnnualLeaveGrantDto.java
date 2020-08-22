package nts.uk.ctx.at.request.app.find.dialog.annualholiday.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnnualLeaveGrantDto {
	/**
	 * 付与日
	 */
	private GeneralDate grantDate;

	/**
	 * 付与数
	 */
	private Double grantNumber;

	/**
	 * 使用日数
	 */
	private Double daysUsedNo;

	/**
	 * 使用時間
	 */
	private Integer usedMinutes;

	/**
	 * 残日数
	 */
	private Double remainDays;

	/**
	 * 残時間
	 */
	private Integer remainMinutes;

	/**
	 * 期限
	 */
	private GeneralDate deadline;
	
	/**
	 * で期限切れ
	 */
	private boolean expiredInCurrentMonthFg;
}