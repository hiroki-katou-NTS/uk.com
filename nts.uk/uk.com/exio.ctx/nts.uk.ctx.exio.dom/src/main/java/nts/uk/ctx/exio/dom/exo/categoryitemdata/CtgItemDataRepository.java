package nts.uk.ctx.exio.dom.exo.categoryitemdata;

import java.util.Optional;
import java.util.List;

/**
* 外部出力カテゴリ項目データ
*/
public interface CtgItemDataRepository
{

    List<CtgItemData> getAllCtgItemData();
    
    List<CtgItemData> getAllByCategoryId(String categoryId);
    
    List<CtgItemData> getAllByKey(String categoryId, String itemNo);

    Optional<CtgItemData> getCtgItemDataById(String categoryId, Integer itemNo);
    
    Optional<CtgItemData> getCtgItemDataByIdAndDisplayClass(String categoryId, Integer itemNo, int displayClassfication);

    void add(CtgItemData domain);

    void update(CtgItemData domain);

    void remove();

}
