package nts.uk.ctx.exio.dom.exo.outitem;

import java.util.List;
import java.util.Optional;

/**
* 出力項目(定型)
*/
public interface StdOutItemRepository
{

    List<StdOutItem> getAllStdOutItem();
    
    Optional<StdOutItem> getStdOutItemByCidAndSetCd(String cid, String condSetCd);

    Optional<StdOutItem> getStdOutItemById(String cid, String outItemCd, String condSetCd);

    void add(StdOutItem domain);

    void update(StdOutItem domain);

    void remove(String cid, String outItemCd, String condSetCd);

}
