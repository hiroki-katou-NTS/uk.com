package nts.uk.ctx.at.request.dom.application.holidayshipment.brkoffsupchangemng;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 振休申請休出変更管理
 * 
 * @author sonnlb
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BrkOffSupChangeMng extends AggregateRoot {
	/**
	 * 振出申請ID
	 */
	private String recAppID;

	/**
	 * 振休申請ID
	 */
	private String absenceLeaveAppID;
}
