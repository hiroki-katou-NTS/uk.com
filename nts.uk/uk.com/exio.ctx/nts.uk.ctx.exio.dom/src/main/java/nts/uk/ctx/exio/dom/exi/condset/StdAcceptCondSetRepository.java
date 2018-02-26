package nts.uk.ctx.exio.dom.exi.condset;

import java.util.Optional;
import java.util.List;

/**
* 受入条件設定（定型）
*/
public interface StdAcceptCondSetRepository
{

    List<StdAcceptCondSet> getAllStdAcceptCondSet();

    Optional<StdAcceptCondSet> getStdAcceptCondSetById(String cid, String conditionSetCd);

    void add(StdAcceptCondSet domain);

    void update(StdAcceptCondSet domain);

    void remove(String cid, String conditionSetCd);

    List<StdAcceptCondSet> getStdAcceptCondSetBySysType(int sysType);
}
