package nts.uk.ctx.pr.shared.ac.wageprovision.processdatecls;

import nts.uk.ctx.pr.shared.dom.adapter.wageprovision.processdatecls.CurrentProcessDateAdapter;
import nts.uk.ctx.pr.shared.dom.adapter.wageprovision.processdatecls.CurrentProcessDateImport;

import javax.ejb.Stateless;
import java.util.List;
@Stateless
public class CurrentProcessDateAdapterImpl implements CurrentProcessDateAdapter {
    @Override
    public List<CurrentProcessDateImport> getCurrProcessDateByKey(String cid, int processCateNo) {
        return null;
    }

    @Override
    public List<CurrentProcessDateImport> getCurrProcessDateByProcessCateNos(String cid, List<Integer> processCateNos) {
        return null;
    }
}
