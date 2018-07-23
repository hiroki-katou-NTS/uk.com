package nts.uk.ctx.exio.dom.exo.outcnddetail;

import java.util.List;

/**
* 検索コードリスト
*/
public interface SearchCodeListRepository
{
    void add(SearchCodeList domain);

    void update(SearchCodeList domain);

    List<SearchCodeList> getSearchCodeByCateIdAndCateNo(String categoryId, Integer categoryNo);

    void remove(String id, String categoryId, int categoryItemNo, int seriNum);
}