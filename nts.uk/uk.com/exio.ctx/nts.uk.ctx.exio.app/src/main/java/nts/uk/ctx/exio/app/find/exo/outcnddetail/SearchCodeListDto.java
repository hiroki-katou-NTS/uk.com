package nts.uk.ctx.exio.app.find.exo.outcnddetail;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.exio.dom.exo.outcnddetail.SearchCodeList;

/**
 * 検索コードリスト
 */
@AllArgsConstructor
@Value
public class SearchCodeListDto {
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

	public static SearchCodeListDto fromDomain(SearchCodeList domain) {
		return new SearchCodeListDto(domain.getId(), domain.getCid(), domain.getConditionSetCode().v(),
				domain.getCategoryId().v(), domain.getCategoryItemNo().v(), domain.getSeriNum(),
				domain.getSearchCode().v(), domain.getSearchItemName());
	}

}
