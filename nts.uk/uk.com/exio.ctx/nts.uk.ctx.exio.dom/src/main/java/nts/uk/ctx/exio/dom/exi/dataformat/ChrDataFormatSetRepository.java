package nts.uk.ctx.exio.dom.exi.dataformat;

import java.util.Optional;
import java.util.List;

/**
* 文字型データ形式設定
*/
public interface ChrDataFormatSetRepository
{

    List<ChrDataFormatSet> getAllChrDataFormatSet();

    Optional<ChrDataFormatSet> getChrDataFormatSetById(String cid, String conditionSetCd, int acceptItemNum);

    void add(ChrDataFormatSet domain);

    void update(ChrDataFormatSet domain);

    void remove(String cid, String conditionSetCd, int acceptItemNum);

}
