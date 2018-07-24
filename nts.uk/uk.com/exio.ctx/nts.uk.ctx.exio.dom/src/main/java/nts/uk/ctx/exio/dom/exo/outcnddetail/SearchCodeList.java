package nts.uk.ctx.exio.dom.exo.outcnddetail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.exio.dom.exo.categoryitemdata.ItemNo;

/**
 * 検索コードリスト
 */
@AllArgsConstructor
@Getter
public class SearchCodeList extends AggregateRoot {

	/**
	 * ID
	 */
	private String id;

	/**
	 * カテゴリID
	 */
	private String categoryId;

	/**
	 * カテゴリ項目NO
	 */
	private ItemNo categoryItemNo;

	/**
	 * 連番
	 */
	private int seriNum;

	/**
	 * 検索コード
	 */
	private ExtOutCndSearchCd searchCode;

	/**
	 * 検索項目名
	 */
	private String searchItemName;

	public SearchCodeList(String id, String categoryId, int categoryItemNo, int seriNum, String searchCode,
			String searchItemName) {
		this.id = id;
		this.categoryId = categoryId;
		this.categoryItemNo = new ItemNo(categoryItemNo);
		this.seriNum = seriNum;
		this.searchCode = new ExtOutCndSearchCd(searchCode);
		this.searchItemName = searchItemName;
	}

}
