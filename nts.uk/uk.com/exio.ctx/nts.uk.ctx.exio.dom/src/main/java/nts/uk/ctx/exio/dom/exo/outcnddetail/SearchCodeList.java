package nts.uk.ctx.exio.dom.exo.outcnddetail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.exio.dom.exo.categoryitemdata.ItemNo;
import nts.uk.ctx.exio.dom.exo.condset.ExternalOutputConditionCode;

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
	 * 会社ID
	 */
	private String cid;

	/**
	 * 条件設定コード
	 */
	private ExternalOutputConditionCode conditionSetCode;

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

	public SearchCodeList(String id, String cid, String cndSetCd, String categoryId, int categoryItemNo, int seriNum,
			String searchCode, String searchItemName) {
		this.id = id;
		this.cid = cid;
		this.conditionSetCode = new ExternalOutputConditionCode(cndSetCd);
		this.categoryId = categoryId;
		this.categoryItemNo = new ItemNo(categoryItemNo);
		this.seriNum = seriNum;
		this.searchCode = new ExtOutCndSearchCd(searchCode);
		this.searchItemName = searchItemName;
	}

}
