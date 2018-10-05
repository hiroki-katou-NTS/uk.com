package nts.uk.ctx.pr.core.dom.wageprovision.processdatecls;

import java.util.Optional;
import java.util.List;

/**
* 支払日の設定の規定値
*/
public interface ValPayDateSetRepository
{

    List<ValPayDateSet> getAllValPayDateSet();

    Optional<ValPayDateSet> getValPayDateSetById(String cid, int processCateNo);

    void add(ValPayDateSet domain);

    void update(ValPayDateSet domain);

    void remove(String cid, int processCateNo);

}
