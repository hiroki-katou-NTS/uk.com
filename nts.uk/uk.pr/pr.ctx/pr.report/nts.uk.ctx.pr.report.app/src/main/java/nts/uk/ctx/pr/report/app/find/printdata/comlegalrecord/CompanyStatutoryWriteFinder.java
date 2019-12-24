package nts.uk.ctx.pr.report.app.find.printdata.comlegalrecord;

import nts.uk.ctx.pr.report.dom.printdata.comlegalrecord.CompanyStatutoryWrite;
import nts.uk.ctx.pr.report.dom.printdata.comlegalrecord.CompanyStatutoryWriteRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Stateless
public class CompanyStatutoryWriteFinder {

    @Inject
    private CompanyStatutoryWriteRepository companyStatutoryWriteRepository;

    public List<CompanyStatutoryWrite> getByCid() {
        return companyStatutoryWriteRepository.getByCid(AppContexts.user().companyId());
    }

    public Optional<CompanyStatutoryWrite> getByKey(String code) {
        return companyStatutoryWriteRepository.getCompanyStatutoryWriteById(AppContexts.user().companyId(), code);
    }

}