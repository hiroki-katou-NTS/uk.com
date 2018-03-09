/**
 * 11:17:10 AM Nov 9, 2017
 */
package nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hungnm
 *
 */
//チェック対象（不可算）
@Getter
@AllArgsConstructor
public class UncountableTarget extends CheckedTarget {
	
	//項目
	private Integer attendanceItem;

}
