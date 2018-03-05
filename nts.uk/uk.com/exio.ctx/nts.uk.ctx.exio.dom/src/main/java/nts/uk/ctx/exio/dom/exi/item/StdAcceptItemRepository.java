package nts.uk.ctx.exio.dom.exi.item;

import java.util.List;
import java.util.Optional;

/**
* 受入項目（定型）
*/
public interface StdAcceptItemRepository
{

//    List<StdAcceptItem> getAllStdAcceptItem();

    Optional<StdAcceptItem> getStdAcceptItemById(String cid, int sysType, String conditionSetCd, String categoryId, int acceptItemNumber);

    void add(StdAcceptItem domain);

    void update(StdAcceptItem domain);

    void remove(String cid, int sysType, String conditionSetCd, String categoryId, int acceptItemNumber);

    List<StdAcceptItem> getListStdAcceptItems(String cid, int systemType, String conditionSetCd);
}
