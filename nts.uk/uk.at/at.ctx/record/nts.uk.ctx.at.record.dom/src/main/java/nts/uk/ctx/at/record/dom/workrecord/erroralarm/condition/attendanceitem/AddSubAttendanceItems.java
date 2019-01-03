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

	public double calculate(Function<List<Integer>, List<Double>> getItemValue) {
		double plus = getItemValue.apply(this.additionAttendanceItems).stream().mapToDouble(c -> c == null ? 0 : c).sum();
		double minus = getItemValue.apply(this.substractionAttendanceItems).stream().mapToDouble(c -> c == null ? 0 : c).sum();
		return plus - minus;
	}
}
