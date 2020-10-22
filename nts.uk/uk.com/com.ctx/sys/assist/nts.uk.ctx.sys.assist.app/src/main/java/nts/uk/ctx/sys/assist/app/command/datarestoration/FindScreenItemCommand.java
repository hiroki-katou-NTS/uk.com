package nts.uk.ctx.sys.assist.app.command.datarestoration;

import java.util.List;

import lombok.Value;
import nts.uk.ctx.sys.assist.app.find.datarestoration.ItemSetDto;

@Value
public class FindScreenItemCommand {
	
	/**
	 * List<項目セット>
	 */
	private List<ItemSetDto> itemSets;
	
	/**
	 * データ復旧処理ID
	 */
	private String dataRecoveryProcessId;
}
