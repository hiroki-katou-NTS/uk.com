package nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.annualleave;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;

/**
 * @author sonnlb
 *
 */
@Value
@AllArgsConstructor
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
