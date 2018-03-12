package nts.uk.ctx.at.request.dom.application.holidayshipment.subtargetdigestion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
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
public class SubTargetDigestion extends AggregateRoot {

	/**
	 * 振出申請ID
	 */
	private String recAppID;

	/**
	 * 振休申請ID
	 */
	private String absenceLeaveAppID;

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

}
