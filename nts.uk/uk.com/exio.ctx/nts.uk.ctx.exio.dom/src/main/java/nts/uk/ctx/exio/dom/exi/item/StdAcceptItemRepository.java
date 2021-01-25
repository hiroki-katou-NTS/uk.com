package nts.uk.ctx.exio.dom.exi.item;

import java.util.List;
import java.util.Optional;

/**
* 受入項目（定型）
*/
public interface StdAcceptItemRepository
{

//    List<StdAcceptItem> getAllStdAcceptItem();

    Optional<StdAcceptItem> getStdAcceptItemById(String cid, String conditionSetCd, int acceptItemNumber);

    void add(StdAcceptItem domain);

    void update(StdAcceptItem domain);
    /**
     * Remove all 受入項目（定型） by 受入条件設定（定型） key, using EntityManager.
     */
    void removeAll(String cid, String conditionSetCd);
    
    /**
     * Remove 受入項目（定型） by primary key.
     */
    void remove(String cid, String conditionSetCd, int acceptItemNumber);

    List<StdAcceptItem> getListStdAcceptItems(String cid, String conditionSetCd);
    
    void addList(List<StdAcceptItem> listItem);
    
}
