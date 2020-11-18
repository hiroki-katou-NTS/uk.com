package nts.uk.ctx.at.record.dom.adapter.shift.estimate.company;

import java.util.Optional;

public interface CompanyEstablishmentAdaptor {
    Optional<CompanyEstablishmentImport> findById(String companyId, int targetYear);
}
