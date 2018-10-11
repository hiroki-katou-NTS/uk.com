package nts.uk.ctx.pr.core.dom.wageprovision.processdatecls;

import java.util.Optional;
import java.util.List;

/**
* 個人処理区分設定
*/
public interface PerProcessClsSetRepository
{

    List<PerProcessClsSet> getAllPerProcessClsSet();

    Optional<PerProcessClsSet> getPerProcessClsSetById(String companyId, int processCateNo);

    void add(PerProcessClsSet domain);

    void update(PerProcessClsSet domain);

    void remove(String companyId, int processCateNo);

}
