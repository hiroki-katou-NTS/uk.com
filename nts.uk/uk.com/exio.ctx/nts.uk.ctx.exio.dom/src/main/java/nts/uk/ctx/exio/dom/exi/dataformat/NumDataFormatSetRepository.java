package nts.uk.ctx.exio.dom.exi.dataformat;

import java.util.Optional;
import java.util.List;

/**
* 数値型データ形式設定
*/
public interface NumDataFormatSetRepository
{

    List<NumDataFormatSet> getAllNumDataFormatSet();

    Optional<NumDataFormatSet> getNumDataFormatSetById(String cid, String conditionSetCd, int acceptItemNum);

    void add(NumDataFormatSet domain);

    void update(NumDataFormatSet domain);

    void remove(String cid, String conditionSetCd, int acceptItemNum);

}
