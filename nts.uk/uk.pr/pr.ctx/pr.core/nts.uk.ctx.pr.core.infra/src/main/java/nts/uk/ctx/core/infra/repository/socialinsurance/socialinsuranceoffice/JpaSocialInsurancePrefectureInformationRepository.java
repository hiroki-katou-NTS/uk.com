package nts.uk.ctx.core.infra.repository.socialinsurance.socialinsuranceoffice;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.core.dom.socialinsurance.socialinsuranceoffice.SocialInsurancePrefectureInformationRepository;

import javax.ejb.Stateless;

@Stateless
public class JpaSocialInsurancePrefectureInformationRepository extends JpaRepository implements SocialInsurancePrefectureInformationRepository {
}
