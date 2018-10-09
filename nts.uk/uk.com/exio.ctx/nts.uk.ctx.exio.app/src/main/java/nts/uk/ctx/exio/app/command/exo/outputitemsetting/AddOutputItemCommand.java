package nts.uk.ctx.exio.app.command.exo.outputitemsetting;

import lombok.Getter;

@Getter
public class AddOutputItemCommand {
	/**
	 * 出力項目コード
	 */
	private int outItemCd;

	/**
	 * 条件設定コード
	 */
	private String condSetCd;

	/**
	 * 出力項目名
	 */
	private String outItemName;

	/**
	 * 項目型
	 */
	private int itemType;

	/**
	 * カテゴリ項目
	 */
	private Integer itemNo;
	
	private Integer categoryId;

}
