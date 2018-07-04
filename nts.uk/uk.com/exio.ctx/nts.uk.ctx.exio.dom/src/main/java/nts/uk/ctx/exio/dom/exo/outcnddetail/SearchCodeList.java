package nts.uk.ctx.exio.dom.exo.outcnddetail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
* 検索コードリスト
*/
@AllArgsConstructor
@Getter
public class SearchCodeList extends AggregateRoot
{
    
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
    private CategoryItemNo categoryItemNo;
    
    /**
    * 検索コード
    */
    private ExtOutCndSearchCd searchCode;
    
    /**
    * 検索項目名
    */
    private String searchItemName;

	public SearchCodeList(String id, String categoryId, Integer categoryItemNo, String searchCode,
			String searchItemName) {
		this.id = id;
		this.categoryId = categoryId;
		this.categoryItemNo = new CategoryItemNo(categoryItemNo);
		this.searchCode = new  ExtOutCndSearchCd(searchCode);
		this.searchItemName = searchItemName;
	}
    
    
    
    
}
