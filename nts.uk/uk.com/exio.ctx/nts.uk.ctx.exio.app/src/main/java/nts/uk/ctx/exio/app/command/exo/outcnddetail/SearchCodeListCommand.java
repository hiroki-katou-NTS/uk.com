package nts.uk.ctx.exio.app.command.exo.outcnddetail;

import lombok.Value;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.exio.dom.exo.outcnddetail.SearchCodeList;

/**
 * 検索コードリスト
 */
@Value
public class SearchCodeListCommand {

	/**
	 * 条件設定コード
	 */
	private String conditionSetCode;

	/**
	 * カテゴリID
	 */
	private int categoryId;

	/**
	 * カテゴリ項目NO
	 */
	private int categoryItemNo;

	/**
	 * 連番
	 */
	private int seriNum;

	/**
	 * 検索コード
	 */
	private String searchCode;

	/**
	 * 検索項目名
	 */
	private String searchItemName;

	public SearchCodeList toDomain(String cid) {
		String id = IdentifierUtil.randomUniqueId();
		return new SearchCodeList(id, cid, this.conditionSetCode, this.categoryId, this.categoryItemNo, this.seriNum,
				this.searchCode, this.searchItemName);
	}
}
