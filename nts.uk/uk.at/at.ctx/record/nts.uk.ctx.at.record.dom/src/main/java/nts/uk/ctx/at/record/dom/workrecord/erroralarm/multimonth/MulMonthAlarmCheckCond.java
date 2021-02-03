package nts.uk.ctx.at.record.dom.workrecord.erroralarm.multimonth;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.attendanceitem.ErAlAttendanceItemCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.SingleValueCompareType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.TypeCheckWorkRecordMultipleMonth;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.MessageDisplay;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.NameAlarmExtractionCondition;

/**
 * 複数月のアラームチェック条件
 * 
 * @author hiep.th
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MulMonthAlarmCheckCond extends AggregateRoot {
	private String cid;
	/** ID */
	private String eralCheckId;
	/**No	 */
	private int condNo;
	
	/** 名称 */
	private NameAlarmExtractionCondition nameAlarmCon;
	
	/** チェック項目の種類 */
	private TypeCheckWorkRecordMultipleMonth typeCheckItem;
	
	private boolean useAtr;
	/** 表示メッセージ */
	private Optional<MessageDisplay> displayMessage;
	
	/**月次のチェック条件*/
	private ErAlAttendanceItemCondition<?> erAlAttendanceItemCondition;
	
	/**
	 * 連続月数
	 */
	private Optional<Integer> continuousMonths;
	/**回数	 */
	private Optional<Integer> numbers;
	/**比較演算子		 */
	private Optional<SingleValueCompareType> compaOperator;
	/*
	
	*//** 複数月のﾁｪｯｸ条件 *//*
	private Optional<MulMonthCheckCond> mulMonthCheckCond;

	*//** 複数月のチェック条件(平均) *//*
	private Optional<MulMonthCheckCondAverage> mulMonthCheckCondAverage;
	
	*//** 複数月のチェック条件(連続) *//*
	private Optional<MulMonthCheckCondContinue> mulMonthCheckCondContinue;
	
	*//** 複数月のチェック条件(該当月数) *//*
	private Optional<MulMonthCheckCondCosp> mulMonthCheckCondCosp;*/	
}
