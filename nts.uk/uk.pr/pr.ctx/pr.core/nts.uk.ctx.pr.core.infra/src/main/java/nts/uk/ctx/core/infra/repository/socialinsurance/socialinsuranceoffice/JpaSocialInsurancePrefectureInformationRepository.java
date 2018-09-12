package nts.uk.ctx.core.infra.repository.socialinsurance.socialinsuranceoffice;

import java.util.List;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.core.dom.socialinsurance.socialinsuranceoffice.SocialInsurancePrefectureInformation;
import nts.uk.ctx.core.dom.socialinsurance.socialinsuranceoffice.SocialInsurancePrefectureInformationRepository;
import nts.uk.ctx.core.infra.entity.socialinsurance.socialinsuranceoffice.QpbmtSocialInsuracePrefectureInfomation;

public class JpaSocialInsurancePrefectureInformationRepository extends JpaRepository
		implements SocialInsurancePrefectureInformationRepository {

	private static final String QUERY = "select * from QpbmtSocialInsuracePrefectureInfomation where socialInsPreInfoPk.historyId  IN"
			+ " (SELECT historyId FROM QpbmtSocialInsurancePrefectureHistory )";

<<<<<<< HEAD
	@Override
	public List<SocialInsurancePrefectureInformation> findByHistory() {
		return this.queryProxy().query(QUERY, QpbmtSocialInsuracePrefectureInfomation.class)
				.getList(c -> c.toDomain(c));
	}
=======
import javax.ejb.Stateless;

@Stateless
public class JpaSocialInsurancePrefectureInformationRepository extends JpaRepository implements SocialInsurancePrefectureInformationRepository {
>>>>>>> d68710f4dfb09f47f45fc50bfa98733219413d35
}
