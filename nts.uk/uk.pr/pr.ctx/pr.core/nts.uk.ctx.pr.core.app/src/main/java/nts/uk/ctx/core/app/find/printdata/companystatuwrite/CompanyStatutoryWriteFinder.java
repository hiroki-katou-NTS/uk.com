package nts.uk.ctx.core.app.find.printdata.companystatuwrite;

import nts.uk.ctx.core.dom.printdata.CompanyStatutoryWrite;
import nts.uk.ctx.core.dom.printdata.CompanyStatutoryWriteRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.CurrProcessDate;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.CurrProcessDateRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.SetDaySupport;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.SetDaySupportRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Stateless
public class CompanyStatutoryWriteFinder {

    @Inject
    private CompanyStatutoryWriteRepository companyStatutoryWriteRepository;

    @Inject
    private CurrProcessDateRepository currProcessDateRepository;

    @Inject
    private SetDaySupportRepository setDaySupportRepository;


    public List<CompanyStatutoryWrite> getByCid() {
        return companyStatutoryWriteRepository.getByCid(AppContexts.user().companyId());
    }

    public Optional<CompanyStatutoryWrite> getByKey(String code){
        return companyStatutoryWriteRepository.getCompanyStatutoryWriteById(AppContexts.user().companyId(),code);
    }

    public Optional<CurrProcessDate> getById(String cid, int processCateNo) {
        return currProcessDateRepository.getByIds(cid,processCateNo);
    }

    public Optional<SetDaySupport> getById(String cid, int processCateNo, int processDate) {
        return setDaySupportRepository.findById(cid,processCateNo,processDate);
    }

}
