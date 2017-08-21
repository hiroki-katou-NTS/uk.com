package nts.uk.ctx.at.request.infra.repository.setting.requestofearch;

import java.util.Optional;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.setting.requestofearch.RequestAppDetailSetting;
import nts.uk.ctx.at.request.dom.setting.requestofearch.RequestOfEarchWorkplace;
import nts.uk.ctx.at.request.dom.setting.requestofearch.RequestOfEarchWorkplaceRepository;
import nts.uk.ctx.at.request.infra.entity.setting.requestofearch.KrqstWpAppConfig;
import nts.uk.ctx.at.request.infra.entity.setting.requestofearch.KrqstWpAppConfigDetail;

public class JpaRequestOfEarchWorkplaceRepository extends JpaRepository implements RequestOfEarchWorkplaceRepository{

	private static final String FIND = "SELECT c "
			+ "FROM KrqstWpAppConfig c "
			+ "WHERE c.KrqstWpAppConfigPK.companyId = :companyId "
			+ "AND c.KrqstWpAppConfigPK.workplaceId = :workplaceId";
	
	private static final String FINDDETAIL = "SELECT c "
			+ "FROM KrqstWpAppConfigDetail c "
			+ "WHERE c.krqstWpAppConfigDetailPK.companyId = :companyId "
			+ "AND c.krqstWpAppConfigDetailPK.workplaceId = :workplaceId "
			+ "AND c.krqstWpAppConfigDetailPK.appType = :appType";

	private RequestOfEarchWorkplace toDomain(KrqstWpAppConfig entity) {
		return RequestOfEarchWorkplace.createSimpleFromJavaType(entity.krqstWpAppConfigPK.companyId,
				entity.krqstWpAppConfigPK.workplaceId,				
				entity.selectOfApproversFlg);
	}
	/**
	 * get request of earch workplace by app type
	 */
	@Override
	public Optional<RequestOfEarchWorkplace> getRequest(String companyId, String workplaceId) {
		Optional<RequestOfEarchWorkplace> data = this.queryProxy()
				.query(FIND, KrqstWpAppConfig.class)
				.setParameter("companyId", companyId)
				.setParameter("workplaceId", workplaceId)
				.getSingle(c -> toDomain(c));
		return data;
	}
	@Override
	public Optional<RequestAppDetailSetting> getRequestDetail(String companyId, String workplaceId, int appType) {
		Optional<RequestAppDetailSetting> data = this.queryProxy()
				.query(FIND, KrqstWpAppConfigDetail.class)
				.setParameter("companyId", companyId)
				.setParameter("workplaceId", workplaceId)
				.setParameter("appType", appType)
				.getSingle(c -> toDomainDetail(c));
		return data;
	}
	private RequestAppDetailSetting toDomainDetail(KrqstWpAppConfigDetail c) {
		return RequestAppDetailSetting.createSimpleFromJavaType(c.krqstWpAppConfigDetailPK.companyId, 
				c.krqstWpAppConfigDetailPK.appType,
				c.memo,
				c.useAtr,
				c.prerequisiteForpauseFlg, 
				c.otAppSettingFlg, 
				c.breakInputFieldDisFlg, 
				c.breakTimeDisFlg, 
				c.atworkTimeBeginDisFlg, 
				c.goOutTimeBeginDisFlg, 
				c.timeCalUseAtr, 
				c.timeInputUseAtr);
	}

}
