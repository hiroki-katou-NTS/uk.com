package nts.uk.ctx.pr.core.dom.wageprovision.processdatecls;

import java.util.List;
import java.util.Optional;

/**
* 現在処理年月
*/
public interface CurrProcessDateRepository
{

    List<CurrProcessDate> getAllCurrProcessDate();

    List<CurrProcessDate> getCurrProcessDateById(String cid, int processCateNo);

    Optional<CurrProcessDate> getCurrProcessDateByIdAndProcessCateNo(String cid, int processCateNo);

    void add(CurrProcessDate domain);

    void update(CurrProcessDate domain);

    void remove(String cid, int processCateNo);

}
