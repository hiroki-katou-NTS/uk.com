package nts.uk.ctx.pr.core.infra.repository.laborinsurance.laborinsuranceoffice;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.laborinsurance.laborinsuranceoffice.PublicEmploymentSecurityOffice;
import nts.uk.ctx.pr.core.dom.laborinsurance.laborinsuranceoffice.PublicEmploymentSecurityOfficeRepository;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaPublicEmploymentSecurityOfficeRepository extends JpaRepository implements PublicEmploymentSecurityOfficeRepository {
    @Override
    public List<PublicEmploymentSecurityOffice> getPublicEmploymentSecurityOfficeByCompany() {
        return null;
    }

    @Override
    public Optional<PublicEmploymentSecurityOffice> getPublicEmploymentSecurityOfficeById(String pubEmpSecOfficeCode) {
        return Optional.empty();
    }

    @Override
    public void add(PublicEmploymentSecurityOffice domain) {

    }

    @Override
    public void update(PublicEmploymentSecurityOffice domain) {

    }

    @Override
    public void remove(String pubEmpSecOfficeCode) {

    }

    @Override
    public List<PublicEmploymentSecurityOffice> getByCidAndCodes(String cid, List<String> codes) {
        return null;
    }
}
