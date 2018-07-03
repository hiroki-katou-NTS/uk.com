package nts.uk.ctx.exio.dom.exo.categoryitemdata;

import java.util.Optional;
import java.util.List;

/**
* 外部出力カテゴリ項目データ
*/
public interface CtgItemDataRepository
{

    List<CtgItemData> getAllCtgItemData();

    Optional<CtgItemData> getCtgItemDataById();

    void add(CtgItemData domain);

    void update(CtgItemData domain);

    void remove();

}
