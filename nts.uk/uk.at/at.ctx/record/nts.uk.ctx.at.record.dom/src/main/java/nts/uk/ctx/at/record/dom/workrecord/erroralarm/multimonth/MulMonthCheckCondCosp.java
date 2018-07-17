package nts.uk.ctx.at.record.dom.workrecord.erroralarm.multimonth;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.CompareRange;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.CompareSingleValue;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.CountableTarget;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.UncountableTarget;

/**
 * @author hiep.th
 * @param <V>
 * @param <V>
 *
 */
// 複数月のチェック条件(該当月数)
@Getter
public class MulMonthCheckCondCosp<V> extends AggregateRoot {

	//勤務実績のエラーアラームチェックID
	private String mulMonAlarnCondID;
	
	//使用区分
	private boolean isUsedFlg;
	
	// チェック対象（可算）
	private CountableTarget countableTarget;

	// チェック対象（不可算）
	private UncountableTarget uncountableTarget;

	// 単一値との比較
	private CompareSingleValue<V> compareSingleValue;

	// 範囲との比較
	private CompareRange<V> compareRange;

	//回数
	private int times;
	
	//比較演算子
	private int compareOperator;
}
