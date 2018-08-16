package nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.rsvleamanager;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

/**
 * @author loivt
 * 積立年休付与情報(仮)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FundingYearHolidayGrantInfor {
	/**
	 * 付与日
	 */
	private GeneralDate grantDate;
	/**
	 * 付与数
	 */
	private Double grandNumber;
	/**
	 * 使用日数
	 */
	private Double daysUsedNo;
	/**
	 * 残日数
	 */
	private Double remainDays;
	
	/**
	 * 期限
	 */
	private GeneralDate deadline;
}
