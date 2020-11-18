package nts.uk.ctx.at.schedule.pubimp.shift.estimate.company;

import nts.uk.ctx.at.schedule.dom.shift.estimate.company.CompanyEstablishment;
import nts.uk.ctx.at.schedule.dom.shift.estimate.company.CompanyEstablishmentRepository;
import nts.uk.ctx.at.schedule.pub.shift.estimate.company.CompanyEstablishmentExport;
import nts.uk.ctx.at.schedule.pub.shift.estimate.company.CompanyEstablishmentPub;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

@Stateless
public class CompanyEstablishmentPubImpl implements CompanyEstablishmentPub {

    @Inject
    private CompanyEstablishmentRepository companyEstablishmentRepo;

    @Override
    public Optional<CompanyEstablishmentExport> findById(String companyId, int targetYear) {
        Optional<CompanyEstablishment> comEstOpt = companyEstablishmentRepo.findById(companyId, targetYear);
        return comEstOpt.map(x -> new CompanyEstablishmentExport(x.getCompanyId().v(), x.getTargetYear().v()));

    }
}
