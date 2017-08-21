package nts.uk.ctx.at.request.infra.repository.setting.requestofearch;

import java.util.Optional;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.setting.requestofearch.RequestOfEarchWorkplace;
import nts.uk.ctx.at.request.dom.setting.requestofearch.RequestOfEarchWorkplaceRepository;
import nts.uk.ctx.at.request.infra.entity.setting.requestofearch.KrqstWpAppConfig;

public class JpaRequestOfEarchWorkplaceRepository extends JpaRepository implements RequestOfEarchWorkplaceRepository{

	private static final String FIND = "SELECT c "
			+ "FROM KrqstWpAppConfig c "
			+ "WHERE c.KrqstWpAppConfigPK.companyId = :companyId "
			+ "AND c.KrqstWpAppConfigPK.workplaceId = :workplaceId"
			+ "AND c.KrqstWpAppConfigPK.appType = :appType";
	/**
	 * get request of earch workplace by app type
	 */
	@Override
	public Optional<RequestOfEarchWorkplace> getRequestByAppType(String companyId, String workplaceId, int appType) {
		Optional<RequestOfEarchWorkplace> data = this.queryProxy()
				.query(FIND, KrqstWpAppConfig.class)
				.setParameter("companyId", companyId)
				.setParameter("workplaceId", workplaceId)
				.setParameter("appType", appType)
				.getSingle(c -> toDomain(c));
		return data;
	}
	private RequestOfEarchWorkplace toDomain(KrqstWpAppConfig entity) {
		return RequestOfEarchWorkplace.createSimpleFromJavaType(entity.krqstWpAppConfigPK.companyId,
				entity.krqstWpAppConfigPK.workplaceId,				
				entity.selectOfApproversFlg);
	}

}
