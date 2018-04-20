package nts.uk.ctx.pereg.app.command.person.setting.selectionitem;

import lombok.Value;

/**
 * 
 * @author tuannv
 *
 */
@Value
public class AddSelectionItemCommand {
	
	/**
	 * 名称
	 */
	private String selectionItemName;
	
	/**
	 * コード型
	 */
	private boolean characterType;
	
	
	/**
	 * コード桁数
	 */
	private int codeLength;
	
	/**
	 * 名称桁数
	 */
	private int nameLength;
	
	/**
	 * 外部コード桁数
	 */
	private int extraCodeLength;
	
	/**
	 *共有 
	 */
	private boolean shareChecked;
	
	/**
	 * 統合コード
	 */
	private String integrationCode;
	
	/**
	 * メモ
	 */
	private String memo;
	
}
