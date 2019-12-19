package nts.uk.ctx.pr.shared.dom.adapter.wageprovision.processdatecls;

import java.util.List;

public interface CurrentProcessDateAdapter {
    List<CurrentProcessDateImport> getCurrProcessDateByKey(String cid, int processCateNo);

    List<CurrentProcessDateImport> getCurrProcessDateByProcessCateNos(String cid, List<Integer> processCateNos);
}
