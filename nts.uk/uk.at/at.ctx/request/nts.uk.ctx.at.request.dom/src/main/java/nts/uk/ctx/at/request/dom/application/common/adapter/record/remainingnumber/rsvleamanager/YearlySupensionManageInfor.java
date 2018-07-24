package nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.rsvleamanager;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

/**
 * @author loivt
 * 積立年休管理情報(仮)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class YearlySupensionManageInfor {
	/**
	 * 年月日
	 */
	private GeneralDate ymd;
	/**
	 * 使用日数
	 */
	private Double dayUseNo;
	
	/**
	 * 使用時間
	 */
	private Integer usedMinute;
	
	/**
	 * 予定実績区分
	 */
	private int scheduleRecordAtr;
}
