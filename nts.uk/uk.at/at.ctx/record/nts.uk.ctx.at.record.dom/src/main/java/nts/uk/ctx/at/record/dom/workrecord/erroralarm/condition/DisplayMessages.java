package nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ColorCode;

/**
 * メッセージの表示方法
 * @author tutk
 *
 */
@Getter
@NoArgsConstructor
public class DisplayMessages {
	private boolean messageBold;
	
	private ColorCode messageColor;

	public DisplayMessages(boolean messageBold, ColorCode messageColor) {
		super();
		this.messageBold = messageBold;
		this.messageColor = messageColor;
	}
	

}
