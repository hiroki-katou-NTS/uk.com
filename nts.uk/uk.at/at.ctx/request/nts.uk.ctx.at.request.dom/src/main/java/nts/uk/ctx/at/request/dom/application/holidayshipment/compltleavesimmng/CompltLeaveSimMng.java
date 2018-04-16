package nts.uk.ctx.at.request.dom.application.holidayshipment.compltleavesimmng;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 振休振出同時申請管理
 * 
 * @author sonnlb
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompltLeaveSimMng extends AggregateRoot {

	/**
	 * 振出申請ID
	 */
	private String recAppID;

	/**
	 * 振休申請ID
	 */
	private String absenceLeaveAppID;
	/**
	 * 同期中
	 */
	private SyncState syncing;
}
