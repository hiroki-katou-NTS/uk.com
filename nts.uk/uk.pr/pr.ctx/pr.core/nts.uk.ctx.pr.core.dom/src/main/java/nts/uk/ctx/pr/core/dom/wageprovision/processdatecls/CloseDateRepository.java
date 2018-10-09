package nts.uk.ctx.pr.core.dom.wageprovision.processdatecls;

import java.util.Optional;
import java.util.List;

/**
* 勤怠締め年月日
*/
public interface CloseDateRepository
{

    List<CloseDate> getAllCloseDate();

    Optional<CloseDate> getCloseDateById(int processCateNo, String cid);

    void add(CloseDate domain);

    void update(CloseDate domain);

    void remove(int processCateNo, String cid);

}
