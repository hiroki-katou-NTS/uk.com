package nts.uk.ctx.at.function.dom.alarm.checkcondition.monthly;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.MessageDisp;

/**
 * 月別実績の抽出条件
 * @author tutk
 *
 */
@Getter
public class ExtraResultMonthly {
	/**ID*/
	private String errorAlarmCheckID;
	/**並び順*/
	private int sortBy;
	/**名称*/
	private NameAlarmExtractionCondition nameAlarmExtraCon;
	/**使用区分*/
	private boolean useAtr;
	/**チェック項目の種類*/
	private TypeMonCheckItem typeCheckItem;
	/**メッセージの表示方法*/
	private HowDisplayMessage howDisplayMessage;
	/**表示メッセージ*/
	private Optional<MessageDisp> displayMessage;
	
	//月次のチェック条件 ???
}
