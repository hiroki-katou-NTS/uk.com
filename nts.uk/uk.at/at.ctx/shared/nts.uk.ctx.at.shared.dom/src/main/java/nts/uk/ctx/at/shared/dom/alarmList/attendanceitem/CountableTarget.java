/**
 * 11:16:56 AM Nov 9, 2017
 */
package nts.uk.ctx.at.shared.dom.alarmList.attendanceitem;

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
