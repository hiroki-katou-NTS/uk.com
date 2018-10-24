package nts.uk.ctx.core.infra.repository.socialinsurance.socialinsuranceoffice;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.core.dom.socialinsurance.socialinsuranceoffice.SocialInsurancePrefectureInformation;
import nts.uk.ctx.core.dom.socialinsurance.socialinsuranceoffice.SocialInsurancePrefectureInformationRepository;
import nts.uk.ctx.core.infra.entity.socialinsurance.socialinsuranceoffice.QpbmtSocialInsuracePrefectureInfomation;

@Stateless
public class JpaSocialInsurancePrefectureInformationRepository extends JpaRepository
		implements SocialInsurancePrefectureInformationRepository {

	private static final String QUERY = "select a from QpbmtSocialInsuracePrefectureInfomation a ";


	@Override
	public List<SocialInsurancePrefectureInformation> findByHistory() {
		return this.queryProxy().query(QUERY, QpbmtSocialInsuracePrefectureInfomation.class)
				.getList(c -> c.toDomain(c));
	}
}