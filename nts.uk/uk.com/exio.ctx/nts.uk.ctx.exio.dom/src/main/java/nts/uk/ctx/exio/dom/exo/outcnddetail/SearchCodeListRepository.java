package nts.uk.ctx.exio.dom.exo.outcnddetail;

/**
* 検索コードリスト
*/
public interface SearchCodeListRepository
{
    void add(SearchCodeList domain);

    void update(SearchCodeList domain);

    void remove(String id, String categoryId, int categoryItemNo);
}