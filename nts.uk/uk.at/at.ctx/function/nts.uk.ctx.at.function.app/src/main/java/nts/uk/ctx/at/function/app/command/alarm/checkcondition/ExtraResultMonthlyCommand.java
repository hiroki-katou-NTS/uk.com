package nts.uk.ctx.at.function.app.command.alarm.checkcondition;

import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.MessageDisp;

@Getter
@Setter
@NoArgsConstructor
public class ExtraResultMonthlyCommand {

	/**ID*/
	private String errorAlarmCheckID;
	/**並び順*/
	private int sortBy;
	/**名称*/
	private String nameAlarmExtraCon;
	/**使用区分*/
	private boolean useAtr;
	/**チェック項目の種類*/
	private int typeCheckItem;
	/**メッセージを太字にする*/
	private boolean messageBold;
	/**メッセージ色*/
	private int messageColor;
	/**表示メッセージ*/
	private Optional<MessageDisp> displayMessage;
	
	
}
