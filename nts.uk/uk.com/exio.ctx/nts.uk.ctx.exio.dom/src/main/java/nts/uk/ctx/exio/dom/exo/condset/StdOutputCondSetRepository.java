package nts.uk.ctx.exio.dom.exo.condset;

import java.util.Optional;
import java.util.List;

/**
* 出力条件設定（定型）
*/
public interface StdOutputCondSetRepository
{

    List<StdOutputCondSet> getAllStdOutputCondSet();
   
    List<StdOutputCondSet> getStdOutCondSetByCid(String cid);
    
    Optional<StdOutputCondSet> getStdOutputCondSetByCid(String cid);

    Optional<StdOutputCondSet> getStdOutputCondSetById(String cid, String conditionSetCd);
    
    List<StdOutputCondSet> getStdOutputCondSetById(String cid, Optional<String>  conditionSetCd);
    
    void add(StdOutputCondSet domain);

    void update(StdOutputCondSet domain);

	void remove(String cid, String conditionSetCd);
}
