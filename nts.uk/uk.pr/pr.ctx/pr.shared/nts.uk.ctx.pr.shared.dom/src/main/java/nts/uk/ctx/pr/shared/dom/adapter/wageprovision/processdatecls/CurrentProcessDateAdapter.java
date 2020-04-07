package nts.uk.ctx.pr.shared.dom.adapter.wageprovision.processdatecls;

import java.util.List;
import java.util.Optional;

public interface CurrentProcessDateAdapter {
    Optional<CurrentProcessDateImport> getCurrProcessDateByKey(String cid, int processCateNo);

    List<CurrentProcessDateImport> getCurrProcessDateByProcessCateNos(String cid, List<Integer> processCateNos);
}
