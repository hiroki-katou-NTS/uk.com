package nts.uk.ctx.exio.dom.exo.outitem;

import java.util.Optional;
import java.util.List;

/**
* 出力項目(定型)
*/
public interface StdOutItemRepository
{

    List<StdOutItem> getAllStdOutItem();

    Optional<StdOutItem> getStdOutItemById(String cid, String outItemCd, String condSetCd);

    void add(StdOutItem domain);

    void update(StdOutItem domain);

    void remove(String cid, String outItemCd, String condSetCd);

}
