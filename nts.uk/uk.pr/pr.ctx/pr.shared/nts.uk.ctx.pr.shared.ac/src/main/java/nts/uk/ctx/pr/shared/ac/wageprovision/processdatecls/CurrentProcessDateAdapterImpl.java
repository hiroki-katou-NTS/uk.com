package nts.uk.ctx.pr.shared.ac.wageprovision.processdatecls;

import nts.uk.ctx.bs.employee.pub.employment.SyEmploymentPub;
import nts.uk.ctx.pr.core.pub.wageprovision.processdatecls.CurrProcessYmPub;
import nts.uk.ctx.pr.shared.dom.adapter.wageprovision.processdatecls.CurrentProcessDateAdapter;
import nts.uk.ctx.pr.shared.dom.adapter.wageprovision.processdatecls.CurrentProcessDateImport;
import sun.util.resources.ru.CurrencyNames_ru_RU;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Stateless
public class CurrentProcessDateAdapterImpl implements CurrentProcessDateAdapter {

    @Inject
    CurrProcessYmPub currProcessYmPub;

    @Override
    public Optional<CurrentProcessDateImport> getCurrProcessDateByKey(String cid, int processCateNo) {
        return currProcessYmPub.getCurrentSalaryProcessYm(cid, processCateNo)
                .map(e -> CurrentProcessDateImport
                        .builder()
                        .cid(e.getCid())
                        .processCateNo(e.getProcessCateNo())
                        .giveCurrTreatYear(e.getCurrentYm())
                        .build());
    }

    @Override
    public List<CurrentProcessDateImport> getCurrProcessDateByProcessCateNos(String cid, List<Integer> processCateNos) {
        return null;
    }
}
