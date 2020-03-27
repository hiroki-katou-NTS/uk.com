package nts.uk.ctx.at.record.app.command.stamp.application;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromptingMessageCommand {
	/** エラー有時に促すメッセージ */
	private String messageContent;
	
	/** メッセージ色 */
	private String messageColor;
}
