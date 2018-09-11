package nts.uk.ctx.core.infra.repository.socialinsurance.socialinsuranceoffice;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.core.dom.socialinsurance.socialinsuranceoffice.SocialInsuranceOfficeRepository;

import javax.ejb.Stateless;

@Stateless
public class JpaSocialInsuranceOfficeRepository extends JpaRepository implements SocialInsuranceOfficeRepository {

}
