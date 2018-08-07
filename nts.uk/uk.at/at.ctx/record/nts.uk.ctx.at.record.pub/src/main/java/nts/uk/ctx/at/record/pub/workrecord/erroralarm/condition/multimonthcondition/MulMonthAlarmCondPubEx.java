package nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.multimonthcondition;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.find.ErAlAtdItemConditionPubExport;

@Getter
@Setter
public class MulMonthAlarmCondPubEx {
	
	/** 勤務実績のエラーアラームチェックID */
	private String errorAlarmCheckID;

	/** 名称 */
	private String nameAlarmMulMon;

	/** 使用区分 */
	private boolean useAtr;

	/** チェック項目の種類 */
	private int typeCheckItem;

	/** メッセージを太字にする */
	private boolean messageBold;

	/** メッセージ色 */
	private String messageColor;

	/** 表示メッセージ */
	private String displayMessage;

	/** 複数月のﾁｪｯｸ条件 */
	private ErAlAtdItemConditionPubExport erAlAtdItem;

	/** 連続月数 */
	private int continuousMonths;
	
	/** 回数 */
	private int times;

	/** 比較演算子 */
	private int compareOperator;

	public MulMonthAlarmCondPubEx(String errorAlarmCheckID, String nameAlarmMulMon, boolean useAtr, int typeCheckItem,
			boolean messageBold, String messageColor, String displayMessage, ErAlAtdItemConditionPubExport erAlAtdItem,
			int continuousMonths, int times, int compareOperator) {
		super();
		this.errorAlarmCheckID = errorAlarmCheckID;
		this.nameAlarmMulMon = nameAlarmMulMon;
		this.useAtr = useAtr;
		this.typeCheckItem = typeCheckItem;
		this.messageBold = messageBold;
		this.messageColor = messageColor;
		this.displayMessage = displayMessage;
		this.erAlAtdItem = erAlAtdItem;
		this.continuousMonths = continuousMonths;
		this.times = times;
		this.compareOperator = compareOperator;
	}
	
}
