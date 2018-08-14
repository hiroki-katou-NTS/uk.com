/**
 * 3:23:22 PM Nov 9, 2017
 */
package nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem;

import java.util.List;
import java.util.function.Function;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * @author hungnm
 *
 */
@Getter
@AllArgsConstructor
public class AddSubAttendanceItems extends DomainObject {

	// 加算する勤怠項目一覧
	private List<Integer> additionAttendanceItems;

	// 減算する勤怠項目一覧
	private List<Integer> substractionAttendanceItems;

	public int calculate(Function<List<Integer>, List<Integer>> getItemValue) {
		int plus = getItemValue.apply(this.additionAttendanceItems).stream().mapToInt(c -> c == null ? 0 : c).sum();
		int minus = getItemValue.apply(this.substractionAttendanceItems).stream().mapToInt(c -> c == null ? 0 : c).sum();
		return plus - minus;
	}
}
