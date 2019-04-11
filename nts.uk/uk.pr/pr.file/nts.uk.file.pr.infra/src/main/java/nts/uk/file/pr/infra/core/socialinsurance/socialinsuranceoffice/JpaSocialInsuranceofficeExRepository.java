package nts.uk.file.pr.infra.core.socialinsurance.socialinsuranceoffice;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.file.app.core.socialinsurance.socialinsuranceoffice.SocialInsuranceOfficeExRepository;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class JpaSocialInsuranceofficeExRepository extends JpaRepository implements SocialInsuranceOfficeExRepository {


    @Override
    public List<Object[]> getSocialInsuranceoffice(String cid) {
        return null;
    }
}
