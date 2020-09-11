package nts.uk.screen.at.app.query.kdp.kdps01.a;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.stamp.application.PromptingMessage;

/**
 * 
 * @author sonnlb
 * 
 *         促すメッセージ
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PromptingMessageDto {

	/** エラー有時に促すメッセージ */
	private String messageContent;

	/** メッセージ色 */
	private String messageColor;

	public static PromptingMessageDto fromDomain(PromptingMessage domain) {
		return new PromptingMessageDto(domain.getMessageContent() != null ? domain.getMessageContent().v() : "",
				domain.getMessageColor() != null ? domain.getMessageColor().v() : "");

	}
}
