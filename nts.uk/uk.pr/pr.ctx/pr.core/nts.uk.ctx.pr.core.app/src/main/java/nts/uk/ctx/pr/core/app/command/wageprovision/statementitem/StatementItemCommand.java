package nts.uk.ctx.pr.core.app.command.wageprovision.statementitem;

import lombok.Value;

@Value
public class StatementItemCommand {

	/**
	 * カテゴリ区分
	 */
	private int categoryAtr;

	/**
	 * 項目名コード
	 */
	private String itemNameCd;

	/**
	 * 既定区分
	 */
	private int defaultAtr;

	/**
	 * 値の属性
	 */
	private int valueAtr;

	/**
	 * 廃止区分
	 */
	private int deprecatedAtr;

	/**
	 * 社会保険対象変更区分
	 */
	private Integer socialInsuaEditableAtr;

	/**
	 * 統合コード
	 */
	private String intergrateCd;

}
