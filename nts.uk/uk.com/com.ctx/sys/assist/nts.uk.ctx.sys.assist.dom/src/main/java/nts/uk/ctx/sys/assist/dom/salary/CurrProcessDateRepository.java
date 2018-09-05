package nts.uk.ctx.sys.assist.dom.salary;

import java.util.Optional;
import java.util.List;

/**
* 現在処理年月
*/
public interface CurrProcessDateRepository
{

    List<CurrProcessDate> getAllCurrProcessDate();

    Optional<CurrProcessDate> getCurrProcessDateById(String cid, int processCateNo);

    void add(CurrProcessDate domain);

    void update(CurrProcessDate domain);

    void remove(String cid, int processCateNo);

}
