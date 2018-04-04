package nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;

/**
 * 消化対象代休管理
 * 
 * @author sonnlb
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubTargetDigestion {

	/**
	 * 申請ID
	 */
	private String appID;
	/**
	 * 使用時間数
	 */
	private GeneralDate hoursUsed;

	/**
	 * 休出管理データ
	 */
	private String leaveMngDataID;
	/**
	 * 休出発生日
	 */
	private GeneralDate breakOutDate;

	/**
	 * 休出状態
	 */
	private ManagementDataAtr restState;

	/**
	 * 日付不明
	 */
	private int unknownDate;
}
