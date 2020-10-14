package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management;


import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfEmployment;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.LaborSystemtAtr;

import java.util.List;
import java.util.Optional;

/**
 * 雇用３６協定時間 IRepository
 */
public interface Employment36HoursRepository {
     void insert(AgreementTimeOfEmployment domain);
     void update(AgreementTimeOfEmployment domain);
     void delete(AgreementTimeOfEmployment domain);
     List<AgreementTimeOfEmployment> getByCid(String cid);
     Optional<AgreementTimeOfEmployment>getByCidAndEmployCode(String cid, String employCode,LaborSystemtAtr laborSystemAtr);
}
