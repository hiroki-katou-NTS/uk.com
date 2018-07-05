package nts.uk.ctx.exio.dom.exo.outcnddetail;

import java.util.List;

/**
* 検索コードリスト
*/
public interface SearchCodeListRepository
{
    void add(SearchCodeList domain);

    void update(SearchCodeList domain);

    void remove(String id, String categoryId, int categoryItemNo);
    
    List<SearchCodeList> getSearchCodeByCateIdAndCateNo(String categoryId, Integer categoryNo);
}