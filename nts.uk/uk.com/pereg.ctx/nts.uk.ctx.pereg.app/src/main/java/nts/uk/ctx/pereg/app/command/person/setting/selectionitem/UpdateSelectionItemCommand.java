package nts.uk.ctx.pereg.app.command.person.setting.selectionitem;

import lombok.Value;

@Value
public class UpdateSelectionItemCommand {
	
	/**
	 * ID
	 */
	private String selectionItemId;
	
	/**
	 * 名称
	 */
	private String selectionItemName;
	
	/**
	 * 統合コード
	 */
	private String integrationCode;
	
	/**
	 * メモ
	 */
	private String memo;
	
}
