package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management;


import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.LaborSystemtAtr;

import java.util.List;
import java.util.Optional;

import java.util.Optional;

/**
 * 会社３６協定時間 Repository
 */
public interface Company36AgreedHoursRepository {
    void insert(AgreementTimeOfCompany domain);
    void update(AgreementTimeOfCompany domain);
    Optional<AgreementTimeOfCompany> getByCid(String cid,LaborSystemtAtr laborSystemAtr);
    List<AgreementTimeOfCompany> find(String cid);
}
