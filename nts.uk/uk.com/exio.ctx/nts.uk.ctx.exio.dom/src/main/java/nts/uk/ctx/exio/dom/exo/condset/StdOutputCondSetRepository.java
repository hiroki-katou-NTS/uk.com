package nts.uk.ctx.exio.dom.exo.condset;

import java.util.Optional;
import java.util.List;

/**
* 出力条件設定（定型）
*/
public interface StdOutputCondSetRepository
{

    List<StdOutputCondSet> getAllStdOutputCondSet();
    
    Optional<StdOutputCondSet> getStdOutputCondSetByCid(String cid);

    Optional<StdOutputCondSet> getStdOutputCondSetById(String cid, String conditionSetCd);
    
    void add(StdOutputCondSet domain);

    void update(StdOutputCondSet domain);

    void remove(String cid, String conditionSetCd);

}
