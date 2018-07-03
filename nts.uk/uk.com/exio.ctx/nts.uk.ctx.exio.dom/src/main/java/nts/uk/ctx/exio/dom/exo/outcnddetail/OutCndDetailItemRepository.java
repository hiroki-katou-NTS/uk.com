package nts.uk.ctx.exio.dom.exo.outcnddetail;

import java.util.List;
import java.util.Optional;

/**
* 出力条件詳細項目
*/
public interface OutCndDetailItemRepository
{

    List<OutCndDetailItem> getAllOutCndDetailItem();

    Optional<OutCndDetailItem> getOutCndDetailItemById(String categoryId, int categoryItemNo);

    void add(OutCndDetailItem domain);

    void update(OutCndDetailItem domain);

    void remove(String categoryId, int categoryItemNo);

}
