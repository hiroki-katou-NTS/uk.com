package nts.uk.ctx.at.schedule.pub.shift.estimate.company;

import java.util.Optional;

/**
 * @author Le Huu Dat
 */
public interface CompanyEstablishmentPub {
    Optional<CompanyEstablishmentExport> findById2(String companyId, int targetYear);
}
