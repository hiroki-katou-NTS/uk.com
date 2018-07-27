package nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition;

import java.util.Optional;

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
	private Optional<String> messageColor;
	public HowDisplayMessage(boolean messageBold, String messageColor) {
		super();
		this.messageBold = messageBold;
		this.messageColor = Optional.ofNullable(messageColor);
	}
	
}
