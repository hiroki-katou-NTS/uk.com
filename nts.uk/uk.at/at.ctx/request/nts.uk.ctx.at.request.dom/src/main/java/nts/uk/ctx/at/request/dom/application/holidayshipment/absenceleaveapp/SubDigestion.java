package nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;

/**
 * 消化対象振休管理
 * 
 * @author sonnlb
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubDigestion {
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

	/**
	 * 日付不明
	 */
	private int unknownDate;
}