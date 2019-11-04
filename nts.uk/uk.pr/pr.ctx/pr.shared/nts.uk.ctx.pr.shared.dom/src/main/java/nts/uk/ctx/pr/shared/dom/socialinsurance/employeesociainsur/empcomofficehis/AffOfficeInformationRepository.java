package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis;

import nts.arc.time.GeneralDate;

import java.util.List;
import java.util.Optional;

/**
* 所属事業所情報
*/
public interface AffOfficeInformationRepository
{

    List<AffOfficeInformation> getAllAffOfficeInformation();

    Optional<AffOfficeInformation> getAffOfficeInformationById(String empID, String hisId);

    void add(AffOfficeInformation domain);

    void update(AffOfficeInformation domain);

    void remove(String socialInsuranceOfficeCd, String hisId);

}
