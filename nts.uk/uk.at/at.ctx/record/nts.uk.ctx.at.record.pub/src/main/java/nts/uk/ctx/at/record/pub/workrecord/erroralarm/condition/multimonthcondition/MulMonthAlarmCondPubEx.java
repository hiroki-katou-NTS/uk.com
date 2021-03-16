package nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.multimonthcondition;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.find.ErAlAtdItemConditionPubExport;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MulMonthAlarmCondPubEx {
	private String cid;
	
	/** 勤務実績のエラーアラームチェックID */
	private String errorAlarmCheckID;

	private int condNo;
	/** 名称 */
	private String nameAlarmMulMon;

	/** 使用区分 */
	private boolean useAtr;

	/** チェック項目の種類 */
	private int typeCheckItem;

	/** 表示メッセージ */
	private String displayMessage;

	/** 複数月のﾁｪｯｸ条件 */
	private ErAlAtdItemConditionPubExport erAlAtdItem;

	/** 連続月数 */
	private Integer continuousMonths;
	
	/** 回数 */
	private Integer times;

	/** 比較演算子 */
	private Integer compareOperator;
	
}
