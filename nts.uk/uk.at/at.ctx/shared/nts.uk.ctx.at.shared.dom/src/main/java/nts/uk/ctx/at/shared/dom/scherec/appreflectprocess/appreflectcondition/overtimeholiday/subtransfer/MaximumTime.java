package nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholiday.subtransfer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * @author thanh_nx
 *
 *         最大の時間
 */
@AllArgsConstructor
@Getter
@Setter
public class MaximumTime {

	// 枠NO
	private int no;

	// 時間
	private AttendanceTime time;

	// 振替時間
	private AttendanceTime transferTime;

}
