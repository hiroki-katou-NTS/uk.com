package nts.uk.ctx.pr.core.dom.wageprovision.processdatecls;

import java.util.Optional;
import java.util.List;

/**
* 個人処理区分設定
*/
public interface PerProcessClsSetRepository
{

    List<PerProcessClsSet> getAllPerProcessClsSet();

    Optional<PerProcessClsSet> getPerProcessClsSetByUIDAndCID(String uid,String cid);

    void add(PerProcessClsSet domain);

    void update(PerProcessClsSet domain);

    void remove(String companyId, String userId);

}
