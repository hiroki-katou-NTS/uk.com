package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis;

import java.util.List;
import java.util.Optional;

/**
* 所属事業所情報
*/
public interface AffOfficeInformationRepository
{

    List<AffOfficeInformation> getAllAffOfficeInformation();
    
    List<AffOfficeInformation> getAllAffOfficeInformationByHistId(String cid, List<String> histIds);

    Optional<AffOfficeInformation> getAffOfficeInformationById(String empID, String hisId);

    List<AffOfficeInformation> getByHistIds(List<String> histIds);

    void add(AffOfficeInformation domain);

    void update(AffOfficeInformation domains);
    
    void updateAll(List<AffOfficeInformation> domains);

    void remove(String socialInsuranceOfficeCd, String hisId);

}
