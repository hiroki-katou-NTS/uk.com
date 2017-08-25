package nts.uk.ctx.at.request.infra.repository.setting.requestofearch;

import java.util.Optional;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.setting.requestofearch.RequestAppDetailSetting;
import nts.uk.ctx.at.request.dom.setting.requestofearch.RequestOfEarchCompany;
import nts.uk.ctx.at.request.dom.setting.requestofearch.RequestOfEarchCompanyRepository;
import nts.uk.ctx.at.request.infra.entity.setting.requestofearch.KrqstComAppConfig;
import nts.uk.ctx.at.request.infra.entity.setting.requestofearch.KrqstComAppConfigDetail;

public class JpaRequestOfEarchCompanyRepository extends JpaRepository implements RequestOfEarchCompanyRepository{

	private static final String FIND = "SELECT c "
			+ "FROM KrqstComAppConfig c "
			+ "WHERE c.krqstComAppConfigPK.companyId = :companyId ";
	private static final String FINDDETAIL = "SELECT C "
			+ "FROM KrqstComAppConfigDetail c "
			+ "WHERE c.krqstWpAppConfigDetailPK.companyId = :companyId "
			+ "AND c.krqstWpAppConfigDetailPK.appType = :appType";

	private RequestOfEarchCompany toDomain(KrqstComAppConfig entity) {
		return RequestOfEarchCompany.createSimpleFromJavaType(entity.companyId,				
				entity.selectOfApproversFlg);
	}
	/**
	 * get request by company
	 */
	@Override
	public Optional<RequestOfEarchCompany> getRequestByCompany(String companyId) {
		Optional<RequestOfEarchCompany> data = this.queryProxy()
				.query(FIND, KrqstComAppConfig.class)
				.setParameter("companyId", companyId)
				.getSingle(c -> toDomain(c));
		return data;
	}
	/**
	 * get request detail
	 */
	@Override
	public Optional<RequestAppDetailSetting> getRequestDetail(String companyId, int appType) {
		Optional<RequestAppDetailSetting> data = this.queryProxy()
				.query(FINDDETAIL, KrqstComAppConfigDetail.class)
				.setParameter("companyId", companyId)
				.setParameter("appType", appType)
				.getSingle(c -> toDomainDetail(c));
		return data;
	}
	private RequestAppDetailSetting toDomainDetail(KrqstComAppConfigDetail c) {
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
