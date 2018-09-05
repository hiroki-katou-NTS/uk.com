package nts.uk.ctx.sys.assist.dom.salary;

import java.util.Optional;
import java.util.List;

/**
* 給与支払日設定
*/
public interface SetDaySupportRepository
{

    List<SetDaySupport> getAllSetDaySupport();

    Optional<SetDaySupport> getSetDaySupportById(String cid, int processCateNo);

    void add(SetDaySupport domain);

    void update(SetDaySupport domain);

    void remove(String cid, int processCateNo);

}
