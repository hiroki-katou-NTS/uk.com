package nts.uk.ctx.at.function.dom.adapter.widgetKtg;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;

@AllArgsConstructor
@Data
public class AnnualLeaveGrantImport {

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
}
