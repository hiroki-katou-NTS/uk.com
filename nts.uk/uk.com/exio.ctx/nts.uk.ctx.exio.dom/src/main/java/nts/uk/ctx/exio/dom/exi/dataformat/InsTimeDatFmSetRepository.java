package nts.uk.ctx.exio.dom.exi.dataformat;

import java.util.Optional;
import java.util.List;

/**
* 時刻型データ形式設定
*/
public interface InsTimeDatFmSetRepository
{

    List<InsTimeDatFmSet> getAllInsTimeDatFmSet();

    Optional<InsTimeDatFmSet> getInsTimeDatFmSetById(String cid, String conditionSetCd, int acceptItemNum);

    void add(InsTimeDatFmSet domain);

    void update(InsTimeDatFmSet domain);

    void remove(String cid, String conditionSetCd, int acceptItemNum);

}
