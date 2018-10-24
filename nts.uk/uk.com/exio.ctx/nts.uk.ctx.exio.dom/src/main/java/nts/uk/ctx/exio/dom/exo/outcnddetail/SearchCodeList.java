package nts.uk.ctx.exio.dom.exo.outcnddetail;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.exio.dom.exo.category.CategoryCd;
import nts.uk.ctx.exio.dom.exo.categoryitemdata.ItemNo;
import nts.uk.ctx.exio.dom.exo.condset.ExternalOutputConditionCode;

/**
 * 検索コードリスト
 */
@NoArgsConstructor
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
	@Setter
	private ExternalOutputConditionCode conditionSetCode;

	/**
	 * カテゴリID
	 */
	private CategoryCd categoryId;

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

	public SearchCodeList(String id, String cid, String cndSetCd, int categoryId, int categoryItemNo, int seriNum,
			String searchCode, String searchItemName) {
		this.id = id;
		this.cid = cid;
		this.conditionSetCode = new ExternalOutputConditionCode(cndSetCd);
		this.categoryId = new CategoryCd(categoryId);
		this.categoryItemNo = new ItemNo(categoryItemNo);
		this.seriNum = seriNum;
		this.searchCode = new ExtOutCndSearchCd(searchCode);
		this.searchItemName = searchItemName;
	}

}
