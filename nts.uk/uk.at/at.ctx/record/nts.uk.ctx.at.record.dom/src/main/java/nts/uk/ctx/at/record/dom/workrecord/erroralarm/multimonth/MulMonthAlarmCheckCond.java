package nts.uk.ctx.at.record.dom.workrecord.erroralarm.multimonth;

import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.HowDisplayMessage;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.MessageDisplay;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.NameAlarmExtractionCondition;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.TypeMonCheckItem;

/**
 * 複数月のアラームチェック条件
 * @author hiep.th
 *
 */
@Getter
public class MulMonthAlarmCheckCond extends AggregateRoot {
	/**ID*/
	private String errorAlarmCheckID;
	/**名称*/
	private NameAlarmExtractionCondition nameAlarmCon;
	/**チェック項目の種類*/
	private TypeMonCheckItem typeCheckItem;
	/**メッセージの表示方法*/
	private HowDisplayMessage howDisplayMessage;
	/**表示メッセージ*/
	private Optional<MessageDisplay> displayMessage;
	
	public MulMonthAlarmCheckCond(String errorAlarmCheckID, NameAlarmExtractionCondition nameAlarmCon,
			TypeMonCheckItem typeCheckItem, HowDisplayMessage howDisplayMessage,
			MessageDisplay displayMessage) {
		super();
		this.errorAlarmCheckID = errorAlarmCheckID;
		this.nameAlarmCon = nameAlarmCon;
		this.typeCheckItem = typeCheckItem;
		this.howDisplayMessage = howDisplayMessage;
		this.displayMessage = Optional.ofNullable(displayMessage);
	}
}
