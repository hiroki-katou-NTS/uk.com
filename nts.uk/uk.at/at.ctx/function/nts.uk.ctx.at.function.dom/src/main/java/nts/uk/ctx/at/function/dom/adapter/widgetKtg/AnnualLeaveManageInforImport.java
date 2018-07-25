package nts.uk.ctx.at.function.dom.adapter.widgetKtg;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;

@AllArgsConstructor
@Data
public class AnnualLeaveManageInforImport {

	/**
	 * 年月日
	 */
	private GeneralDate ymd;
	
	/**
	 * 使用日数
	 */
	private Double daysUsedNo;
	
	/**
	 * 使用時間
	 */
	private Integer usedMinutes;
	
	/**
	 * 予定実績区分
	 */
	private int scheduleRecordAtr;
}
