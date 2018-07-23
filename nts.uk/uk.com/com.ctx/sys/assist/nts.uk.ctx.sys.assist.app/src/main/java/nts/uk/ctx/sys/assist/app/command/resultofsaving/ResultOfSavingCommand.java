package nts.uk.ctx.sys.assist.app.command.resultofsaving;

import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * データ保存の保存結果
 */
@AllArgsConstructor
@Value
public class ResultOfSavingCommand {

	/**
	 * データ保存処理ID
	 */
	private String storeProcessingId;

	
	private String fileId;

}
