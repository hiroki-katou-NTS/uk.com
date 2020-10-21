package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management;


import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.LaborSystemtAtr;

import java.util.List;
import java.util.Optional;

/**
 * 	Repository	 職場３６協定時間
 */
public interface Workplace36AgreedHoursRepository {
    void insert(AgreementTimeOfWorkPlace domain);
    void update(AgreementTimeOfWorkPlace domain);
    void delete(AgreementTimeOfWorkPlace domain);
    List<AgreementTimeOfWorkPlace> getByListWorkplaceId(List<String> listWorkplaceId);
    List<AgreementTimeOfWorkPlace> getByListWorkplaceId(String workplaceId);
    Optional<AgreementTimeOfWorkPlace> getByWorkplaceId(String workplaceId,LaborSystemtAtr laborSystemAtr);

    List<String> findWorkPlaceSetting(LaborSystemtAtr laborSystemAtr);
}
