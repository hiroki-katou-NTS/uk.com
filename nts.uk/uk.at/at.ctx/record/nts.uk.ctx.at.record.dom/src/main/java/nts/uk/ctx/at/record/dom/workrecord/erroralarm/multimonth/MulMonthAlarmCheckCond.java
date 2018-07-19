package nts.uk.ctx.at.record.dom.workrecord.erroralarm.multimonth;

import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.TypeCheckWorkRecordMultipleMonth;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.HowDisplayMessage;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.MessageDisplay;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.NameAlarmExtractionCondition;

/**
 * 複数月のアラームチェック条件
 * 
 * @author hiep.th
 *
 */
@Getter
public class MulMonthAlarmCheckCond extends AggregateRoot {
	/** ID */
	private String errorAlarmCheckID;
	/** 名称 */
	private NameAlarmExtractionCondition nameAlarmCon;
	/** チェック項目の種類 */
	private TypeCheckWorkRecordMultipleMonth typeCheckItem;
	/** メッセージの表示方法 */
	private HowDisplayMessage howDisplayMessage;
	/** 表示メッセージ */
	private Optional<MessageDisplay> displayMessage;

	/** 複数月のﾁｪｯｸ条件 */
	private Optional<MulMonthCheckCond> mulMonthCheckCond;

	/** 複数月のチェック条件(平均) */
	private Optional<MulMonthCheckCondAverage> mulMonthCheckCondAverage;
	
	/** 複数月のチェック条件(連続) */
	private Optional<MulMonthCheckCondContinue> mulMonthCheckCondContinue;
	
	/** 複数月のチェック条件(該当月数) */
	private Optional<MulMonthCheckCondCosp> mulMonthCheckCondCosp;
	
	public MulMonthAlarmCheckCond(String errorAlarmCheckID, NameAlarmExtractionCondition nameAlarmCon,
			TypeCheckWorkRecordMultipleMonth typeCheckItem, HowDisplayMessage howDisplayMessage, MessageDisplay displayMessage) {
		super();
		this.errorAlarmCheckID = errorAlarmCheckID;
		this.nameAlarmCon = nameAlarmCon;
		this.typeCheckItem = typeCheckItem;
		this.howDisplayMessage = howDisplayMessage;
		this.displayMessage = Optional.ofNullable(displayMessage);
	}
//	
	
	public MulMonthAlarmCheckCond(String errorAlarmCheckID, NameAlarmExtractionCondition nameAlarmCon,
			TypeCheckWorkRecordMultipleMonth typeCheckItem, HowDisplayMessage howDisplayMessage, MessageDisplay displayMessage,
			MulMonthCheckCond mulMonthCheckCond,
			MulMonthCheckCondAverage mulMonthCheckCondAverage, 
			MulMonthCheckCondContinue mulMonthCheckCondContinue,
			MulMonthCheckCondCosp mulMonthCheckCondCosp) {
		super();
		this.errorAlarmCheckID = errorAlarmCheckID;
		this.nameAlarmCon = nameAlarmCon;
		this.typeCheckItem = typeCheckItem;
		this.howDisplayMessage = howDisplayMessage;
		this.displayMessage = Optional.ofNullable(displayMessage);
		this.mulMonthCheckCond = Optional.ofNullable(mulMonthCheckCond);
		this.mulMonthCheckCondAverage = Optional.ofNullable(mulMonthCheckCondAverage);
		this.mulMonthCheckCondContinue = Optional.ofNullable(mulMonthCheckCondContinue);
		this.mulMonthCheckCondCosp = Optional.ofNullable(mulMonthCheckCondCosp);
	}
	
}
