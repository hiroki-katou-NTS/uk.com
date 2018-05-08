package nts.uk.ctx.at.function.dom.alarm.checkcondition.monthly;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
/**
 * メッセージの表示方法
 * @author tutk
 *
 */
@Getter
public class HowDisplayMessage extends DomainObject {
	/**メッセージを太字にする*/
	private boolean messageBold;
	/**メッセージ色*/
	private int messageColor;
	public HowDisplayMessage(boolean messageBold, int messageColor) {
		super();
		this.messageBold = messageBold;
		this.messageColor = messageColor;
	}
	
}
