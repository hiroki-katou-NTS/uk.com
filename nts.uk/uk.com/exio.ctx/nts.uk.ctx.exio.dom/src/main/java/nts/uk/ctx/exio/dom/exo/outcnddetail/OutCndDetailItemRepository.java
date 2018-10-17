package nts.uk.ctx.exio.dom.exo.outcnddetail;

import java.util.List;
import java.util.Optional;

/**
* 出力条件詳細項目
*/
public interface OutCndDetailItemRepository
{

    List<OutCndDetailItem> getAllOutCndDetailItem();

    List<OutCndDetailItem> getOutCndDetailItemByCode(String code);
    
    List<OutCndDetailItem> getOutCndDetailItemByCidAndCode(String cid, String code);
    
    Optional<OutCndDetailItem> getOutCndDetailItemById(int categoryId, int categoryItemNo, String conditionSettingCd);

    void add(OutCndDetailItem domain);
    
    void add(List<OutCndDetailItem> domain);

    void update(OutCndDetailItem domain);

    void remove(String cid, String conditionSettingCd, int categoryId, int categoryItemNo, int seriNum);
    
    void remove(List<OutCndDetailItem> listOutCndDetailItem);

}
