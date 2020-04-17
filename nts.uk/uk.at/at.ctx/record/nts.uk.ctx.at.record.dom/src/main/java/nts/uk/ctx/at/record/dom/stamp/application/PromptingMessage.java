package nts.uk.ctx.at.record.dom.stamp.application;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.color.ColorCode;

/**
 * 促すメッセージの設定
 * @author phongtq
 *
 */

@Getter
public class PromptingMessage {

	/** エラー有時に促すメッセージ */
	private MessageContent messageContent;
	
	/** メッセージ色 */
	private ColorCode messageColor;

	public PromptingMessage(MessageContent messageContent, ColorCode messageColor) {
		super();
		this.messageContent = messageContent;
		this.messageColor = messageColor;
	}
}
