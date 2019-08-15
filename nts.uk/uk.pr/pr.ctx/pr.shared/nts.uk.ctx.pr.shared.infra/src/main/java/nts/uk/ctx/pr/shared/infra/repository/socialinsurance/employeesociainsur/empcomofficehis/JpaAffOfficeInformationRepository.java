package nts.uk.ctx.pr.shared.infra.repository.socialinsurance.employeesociainsur.empcomofficehis;

import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.AffOfficeInformation;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empcomofficehis.AffOfficeInformationRepository;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;


@Stateless
public class JpaAffOfficeInformationRepository implements AffOfficeInformationRepository{
    @Override
    public List<AffOfficeInformation> getAllAffOfficeInformation() {
        return null;
    }

    @Override
    public Optional<AffOfficeInformation> getAffOfficeInformationById(String socialInsuranceOfficeCd, String hisId) {
        return Optional.empty();
    }

    @Override
    public void add(AffOfficeInformation domain) {

    }

    @Override
    public void update(AffOfficeInformation domain) {

    }

    @Override
    public void remove(String socialInsuranceOfficeCd, String hisId) {

    }
}
