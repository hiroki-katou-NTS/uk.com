package nts.uk.ctx.at.request.dom.application.holidayshipment.subdigestion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.holidayshipment.subtargetdigestion.ManagementDataAtr;

/**
 * 消化対象振休管理
 * 
 * @author sonnlb
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubDigestion extends AggregateRoot {
	/**
	 * 振休申請ID
	 */
	private String absenceLeaveAppID;

	/**
	 * 使用日数
	 */
	private ManagementDataDaysAtr daysUsedNo;

	/**
	 * 振出管理データ
	 */
	private String payoutMngDataID;

	/**
	 * 振出状態
	 */
	private ManagementDataAtr pickUpState;

	/**
	 * 振休発生日
	 */
	private GeneralDate occurrenceDate;
}
