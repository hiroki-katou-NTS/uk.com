package nts.uk.ctx.at.record.ac.shift.estimate.company;

import nts.uk.ctx.at.record.dom.adapter.shift.estimate.company.CompanyEstablishmentAdaptor;
import nts.uk.ctx.at.record.dom.adapter.shift.estimate.company.CompanyEstablishmentImport;
import nts.uk.ctx.at.schedule.pub.shift.estimate.company.CompanyEstablishmentExport;
import nts.uk.ctx.at.schedule.pub.shift.estimate.company.CompanyEstablishmentPub;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
public class CompanyEstablishmentAcAdaptor implements CompanyEstablishmentAdaptor {

    @Inject
    private CompanyEstablishmentPub companyEstablishmentPub;

    @Override
    public Optional<CompanyEstablishmentImport> findById(String companyId, int targetYear) {
        Optional<CompanyEstablishmentExport> comEstOpt = companyEstablishmentPub.findById(companyId, targetYear);
        return comEstOpt.map(x -> new CompanyEstablishmentImport(x.getCompanyId(), x.getTargetYear()));
    }
}
