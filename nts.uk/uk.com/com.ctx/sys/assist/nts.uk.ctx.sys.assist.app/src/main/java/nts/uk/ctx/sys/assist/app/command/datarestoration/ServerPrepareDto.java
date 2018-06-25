package nts.uk.ctx.sys.assist.app.command.datarestoration;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ServerPrepareDto {
	public int processingType;
	public int processingStatus;
	public int conditionValue;
	public String conditionName;
	public String messageId;
}
