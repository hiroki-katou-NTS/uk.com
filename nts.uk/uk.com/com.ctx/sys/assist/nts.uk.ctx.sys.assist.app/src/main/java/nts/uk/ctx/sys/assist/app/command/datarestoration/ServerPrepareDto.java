package nts.uk.ctx.sys.assist.app.command.datarestoration;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ServerPrepareDto {
	
	
	/**
	 * 0: Uploading
	 * 1: Extracting
	 * 2: Validating
	 */
	public int processingType;
	/**
	 * 0: Processing
	 * 1: Success
	 * 2: Failed
	 */
	public int processingStatus;
	public int conditionValue;
	public String conditionName;
	public String messageId;
}
