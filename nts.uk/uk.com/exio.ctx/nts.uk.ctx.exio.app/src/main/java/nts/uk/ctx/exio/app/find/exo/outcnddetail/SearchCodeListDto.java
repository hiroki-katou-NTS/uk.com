package nts.uk.ctx.exio.app.find.exo.outcnddetail;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.exio.dom.exo.outcnddetail.SearchCodeList;

/**
* 検索コードリスト
*/
@AllArgsConstructor
@Value
public class SearchCodeListDto
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
    private int categoryItemNo;
    
    /**
    * 検索コード
    */
    private String searchCode;
    
    /**
    * 検索項目名
    */
    private String searchItemName;

	public static SearchCodeListDto fromDomain(SearchCodeList domain)
    {
        return new SearchCodeListDto(domain.getId(), domain.getCategoryId(), domain.getCategoryItemNo().v(), domain.getSearchCode().v(), domain.getSearchItemName());
    }
    
}
