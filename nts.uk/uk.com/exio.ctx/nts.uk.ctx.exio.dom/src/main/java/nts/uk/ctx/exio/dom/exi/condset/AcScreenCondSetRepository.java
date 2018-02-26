package nts.uk.ctx.exio.dom.exi.condset;

import java.util.Optional;
import java.util.List;

/**
* 受入選別条件設定
*/
public interface AcScreenCondSetRepository
{

    List<AcScreenCondSet> getAllAcScreenCondSet();

    Optional<AcScreenCondSet> getAcScreenCondSetById(String cid, String conditionSetCd, int acceptItemNum);

    void add(AcScreenCondSet domain);

    void update(AcScreenCondSet domain);

    void remove(String cid, String conditionSetCd, int acceptItemNum);

}
