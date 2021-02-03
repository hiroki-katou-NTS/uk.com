package nts.uk.ctx.at.record.dom.workrecord.erroralarm.multimonth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.ErAlAttendanceItemCondition;

/**
 * @author hiep.th
 * @param <V>
 * @param <V>
 *
 */
// 複数月のチェック条件(連続)
@Getter
@AllArgsConstructor
public class MulMonthCheckCondContinue extends AggregateRoot {

	//勤務実績のエラーアラームチェックID
	private String errorAlarmCheckID;
	
	//使用区分
	private boolean isUsedFlg;

	//連続月数
	private int continuousMonths;
	
	/**月次のチェック条件*/
	private ErAlAttendanceItemCondition<?> erAlAttendanceItemCondition;
}
