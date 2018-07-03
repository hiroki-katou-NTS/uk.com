package nts.uk.ctx.exio.dom.exo.outcnddetail;

import java.util.List;
import java.util.Optional;

/**
* 出力条件詳細(定型)
*/
public interface OutCndDetailRepository
{

    List<OutCndDetail> getAllOutCndDetail();
    
    Optional<OutCndDetail> getOutCndDetailByCode(String code);

    Optional<OutCndDetail> getOutCndDetailById(String cid, String companyCndSetCd);

    void add(OutCndDetail domain);

    void update(OutCndDetail domain);

    void remove(String cid, String companyCndSetCd);

}
