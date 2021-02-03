/**
 * 11:16:56 AM Nov 9, 2017
 */
package nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hungnm
 *
 */
//チェック対象（可算）
@Getter
@AllArgsConstructor
public class CountableTarget extends CheckedTarget{

	//チェック対象
	private AddSubAttendanceItems addSubAttendanceItems;
	
}
