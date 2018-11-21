package nts.uk.ctx.pereg.dom.person.setting.selectionitem.selectionitem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SelectionItemReportData {

	/**
	 * 名称
	 */
	private String selectionItemName;

	/**
	 * 選択肢コードの文字種
	 */
	private Integer characterType;
	
	/**
	 * 選択肢コード
	 */
	private Integer codeLength;
	
	/**
	 * 選択肢名称
	 */
	private Integer nameLength;
	
	/**
	 * 選択肢外部コード
	 */
	private Integer externalCodeLength;

	/**
	 * 統合コード
	 */
	private String integrationCode;

	/**
	 * メモ
	 */
	private String memo;
	
	
}
